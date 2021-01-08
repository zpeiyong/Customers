package com.dataint.topic.service.impl;

import com.dataint.cloud.common.exception.DataAlreadyExistException;
import com.dataint.cloud.common.exception.DataNotExistException;
import com.dataint.cloud.common.model.Pagination;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.topic.db.dao.IKeywordDao;
import com.dataint.topic.db.dao.ITopicDao;
import com.dataint.topic.db.dao.ITopicKeywordDao;
import com.dataint.topic.db.entity.Keywords;
import com.dataint.topic.db.entity.Topic;
import com.dataint.topic.db.entity.TopicKeyword;
import com.dataint.topic.model.form.TopicForm;
import com.dataint.topic.model.form.UpdateTopicForm;
import com.dataint.topic.model.vo.TopicVO;
import com.dataint.topic.service.ISpiderService;
import com.dataint.topic.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements ITopicService {
    @Autowired
    private ITopicDao topicDao;
    @Autowired
    private IKeywordDao keywordDao;
    @Autowired
    private ITopicKeywordDao topicKeywordDao;
    @Autowired
    private ISpiderService spiderService;

    @Override
    public TopicVO addTopic(TopicForm topicForm) {
        // 查询专题名称是否已存在
        Topic topic = topicDao.findByName(topicForm.getName());
        if (topic != null) {
            throw new DataAlreadyExistException("专题名称重复了，请重新提交！");
        }

        // 准备keywordIdList和新添加的关键词列表(用于发布新的一次性爬虫脚本)
        List<Long> keywordIdList = new ArrayList<>();
        List<String> newKeywordNameList = new ArrayList<>();
        for (String keywordName: topicForm.getKeywordNames()) {
            // 查询关键词是否已存在, 不存在则先添加关键词
            Keywords keywords = keywordDao.findByName(keywordName);
            if (keywords == null) {
                keywords = new Keywords();
                keywords.setName(keywordName);
                keywordDao.save(keywords);

                // 新添加的关键词
                newKeywordNameList.add(keywordName);
            }
            keywordIdList.add(keywords.getId());
        }

        // 写topic和关系表
        topic = new Topic();
        topic.setName(topicForm.getName());
        topicDao.save(topic);

        // 写topic-keyword关系表
        for (Long keywordId : keywordIdList) {
            TopicKeyword topicKeyword = new TopicKeyword();
            topicKeyword.setKeywordId(keywordId);
            topicKeyword.setTopicId(topic.getId());

            topicKeywordDao.save(topicKeyword);
        }

//        // 新添加的关键词发布一次性爬虫脚本
//        if (!CollectionUtils.isEmpty(newKeywordNameList)) {
//            spiderService.pubDisposeProjects(newKeywordNameList);
//        }

        // 构造返回值
        TopicVO topicVO = new TopicVO(topic);
        List<Keywords> keywordsList = keywordDao.findAllByTopicId(topic.getId());
        if (keywordsList.size() > 0)
            topicVO.setKeywordNames(keywordsList.stream().map(Keywords::getName).collect(Collectors.toList()));

        return topicVO;
    }

    @Override
    public ResultVO getAllTopic(Integer current, Integer pageSize, String keyword) {
        Page<Topic> topicPage = topicDao.findAllByEnableAndNameContainingOrderByCreatedTimeDesc(true, keyword, PageRequest.of(current - 1, pageSize));

        List<TopicVO> topicList = topicPage.getContent().stream().map(TopicVO::new).collect(Collectors.toList());
        for (TopicVO topicVO : topicList) {
            List<Keywords> keywordList = keywordDao.findAllByTopicId(topicVO.getId());
            if (keywordList.size() > 0)
                topicVO.setKeywordNames(keywordList.stream().map(Keywords::getName).collect(Collectors.toList()));
        }

        Pagination pagination = new Pagination();
        pagination.setCurrent(current);
        pagination.setPageSize(pageSize);
        pagination.setTotal(topicPage.getTotalElements());

        return ResultVO.success(topicList, pagination);
    }

    @Override
    public TopicVO delTopic(Long id) {
        // check if topic exist
        Optional<Topic> topicOpt = topicDao.findById(id);
        if (!topicOpt.isPresent()) {
            throw new DataNotExistException("专题组不存在!");
        }
        Topic topic = topicOpt.get();

        // 设置逻辑删除
        topic.setIfDeleted(true);
        topic.setUpdatedTime(new Date());
        topicDao.save(topic);

        // 构造返回值
        TopicVO topicVO = new TopicVO(topic);
        List<Keywords> keywordsList = keywordDao.findAllByTopicId(topic.getId());
        if (keywordsList.size() > 0)
            topicVO.setKeywordNames(keywordsList.stream().map(Keywords::getName).collect(Collectors.toList()));

        return topicVO;
    }

    @Override
    public List<Topic> getAllActiveTopic() {
        return topicDao.findAllByEnableAndIfDeleted(true, false);
    }

    @Override
    public List<Topic> getAllDeleteTopic() {
        return topicDao.findAllByEnableAndIfDeleted(true, true);
    }

    @Transactional
    @Override
    public TopicVO updateTopic(UpdateTopicForm updateTopicForm) {
        // 查询专题名称是否已存在
        Topic topic = topicDao.findByName(updateTopicForm.getName());
        if (topic != null && topic.getId() != updateTopicForm.getId()) {
            throw new DataAlreadyExistException("专题名称重复了，请重新提交！");
        }

        // check if topic exist
        Optional<Topic> topicOpt = topicDao.findById(updateTopicForm.getId());
        if (!topicOpt.isPresent()) {
            throw new DataNotExistException("专题组不存在!");
        }
        topic = topicOpt.get();

        // 更新并保存topic数据
        topic.setUpdatedTime(new Date());
        topic.setName(updateTopicForm.getName());
        if (updateTopicForm.getIfDeleted() != null) {
            topic.setIfDeleted(updateTopicForm.getIfDeleted());
        }
        topicDao.save(topic);

        // topic-keyword关系维护
        List<String> keywordNameList = updateTopicForm.getKeywordNames();

        if (CollectionUtils.isEmpty(keywordNameList)) {
            topicKeywordDao.deleteAllByTopicId(topic.getId());
        } else {
            // 先维护keyword表, 获得所有的keywordId
            List<Long> keywordIdList = new ArrayList<>();
            List<String> newKeywordNameList = new ArrayList<>();
            for (String keywordName: keywordNameList) {
                // 查询关键词是否已存在, 不存在则先添加关键词
                Keywords keywords = keywordDao.findByName(keywordName);
                if (keywords == null) {
                    keywords = new Keywords();
                    keywords.setName(keywordName);
                    keywordDao.save(keywords);

                    // 新添加的关键词
                    newKeywordNameList.add(keywordName);
                }
                keywordIdList.add(keywords.getId());
            }

            // 遍历已存在的topic-keyword关系列表, 过滤出需要删除和新添加的关系
            List<TopicKeyword> oldTkList = topicKeywordDao.findAllByTopicId(topic.getId());
            Map<Long, TopicKeyword> oldTkMap = new HashMap<>();
            oldTkList.forEach(topicKeyword -> oldTkMap.put(topicKeyword.getKeywordId(), topicKeyword));

            List<TopicKeyword> addTkList = new ArrayList<>();
            for (Long keywordId : keywordIdList) {
                // 若关系已经存在则删除对应的oldTkMap中的记录, 若关系不存在则维护新添加关系列表
                if (oldTkMap.containsKey(keywordId)) {
                    oldTkMap.remove(keywordId);
                } else {
                    TopicKeyword topicKeyword = new TopicKeyword(topic.getId(), keywordId);
                    addTkList.add(topicKeyword);
                }
            }

            // oldTkMap剩余元素为需要删除的关系
            if (!CollectionUtils.isEmpty(oldTkMap)) {
                topicKeywordDao.deleteAll(oldTkMap.values());
            }

            // addTkList元素为需要添加的关系
            if (!CollectionUtils.isEmpty(addTkList)) {
                topicKeywordDao.saveAll(addTkList);
            }

//            // 新添加的关键词发布一次性爬虫脚本
//            if (!CollectionUtils.isEmpty(newKeywordNameList)) {
//                spiderService.pubDisposeProjects(newKeywordNameList);
//            }
        }

        // 构造返回值
        TopicVO topicVO = new TopicVO(topic);
        List<Keywords> keywordsList = keywordDao.findAllByTopicId(topic.getId());
        if (keywordsList.size() > 0)
            topicVO.setKeywordNames(keywordsList.stream().map(Keywords::getName).collect(Collectors.toList()));

        return topicVO;
    }

    @Override
    public TopicVO recoveryTopic(Long id) {
        // check if topic exist
        Optional<Topic> topicOpt = topicDao.findById(id);
        if (!topicOpt.isPresent()) {
            throw new DataNotExistException("专题组不存在!");
        }
        Topic topic = topicOpt.get();

        // 设置逻辑恢复
        topic.setIfDeleted(false);
        topic.setUpdatedTime(new Date());
        topicDao.save(topic);

        // 构造返回值
        TopicVO topicVO = new TopicVO(topic);
        List<Keywords> keywordsList = keywordDao.findAllByTopicId(id);
        if (keywordsList.size() > 0)
            topicVO.setKeywordNames(keywordsList.stream().map(Keywords::getName).collect(Collectors.toList()));

        return topicVO;
    }
}
