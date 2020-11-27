package com.dataint.topic.service.impl;

import com.dataint.cloud.common.model.Pagination;
import com.dataint.topic.db.dao.ITopicDao;
import com.dataint.topic.db.dao.ITopicKeywordDao;
import com.dataint.topic.db.entity.Topic;
import com.dataint.topic.db.entity.TopicKeyword;
import com.dataint.topic.model.form.TopicForm;
import com.dataint.topic.model.form.UpdateTopicForm;
import com.dataint.topic.model.vo.TopicVO;
import com.dataint.topic.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements ITopicService {
    @Autowired
    private ITopicDao topicDao;
    @Autowired
    private ITopicKeywordDao topicKeywordDao;
    @Override
    public Object addTopic(TopicForm topicForm) {
        List<Topic> topicList = topicDao.findAll();
        List<String> topicNames = topicList.stream().map(Topic::getName).collect(Collectors.toList());
        Boolean flag = topicNames.contains(topicForm.getName());
        if (flag) {
            return "专题名称重复了，请重新提交！";
        }else{
            Topic topic = new Topic();
            topic.setName(topicForm.getName());
            Topic topic1 = topicDao.save(topic);
            for (String keyword: topicForm.getKeywordNames()) {
                TopicKeyword topicKeyword = new TopicKeyword();
                topicKeyword.setTopicId(topic1.getId());
                topicKeyword.setName(keyword);
                topicKeywordDao.save(topicKeyword);
            }
            TopicVO topicVO = new TopicVO(topic1);
            List<TopicKeyword> topicKeywordList = topicKeywordDao.findAllByEnableAndTopicId(true, topic1.getId());
            if (topicKeywordList.size() > 0)
                topicVO.setKeywordNames(topicKeywordList.stream().map(TopicKeyword::getName).collect(Collectors.toList()));
            return topicVO;
        }
    }

    @Override
    public Map<String, Object> getAllTopic(Integer current, Integer pageSize, String keyword) {
        Page<Topic> topicPage = topicDao.findAllByEnableAndNameContainingOrderByCreatedTimeDesc(true, keyword, PageRequest.of(current - 1, pageSize));
        List<TopicVO> topicList = topicPage.getContent().stream().map(TopicVO::new).collect(Collectors.toList());
        for (TopicVO topicVO : topicList) {
            List<TopicKeyword> topicKeywordList = topicKeywordDao.findAllByEnableAndTopicId(true, topicVO.getId());
            if (topicKeywordList.size() > 0)
                topicVO.setKeywordNames(topicKeywordList.stream().map(TopicKeyword::getName).collect(Collectors.toList()));
        }
        Pagination pagination = new Pagination();
        pagination.setCurrent(current);
        pagination.setPageSize(pageSize);
        pagination.setTotal(topicPage.getTotalElements());
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("list", topicList);
        stringObjectMap.put("pagination", pagination);
        return stringObjectMap;
    }


    @Override
    public TopicVO delTopic(Long id) {
        Topic topic = topicDao.getOne(id);
        topic.setIfDeleted(true);
        topic.setUpdatedTime(new Date());
        Topic topic1 = topicDao.save(topic);
        TopicVO topicVO = new TopicVO(topic1);
        List<TopicKeyword> topicKeywordList = topicKeywordDao.findAllByEnableAndTopicId(true, topic1.getId());
        if (topicKeywordList.size() > 0)
            topicVO.setKeywordNames(topicKeywordList.stream().map(TopicKeyword::getName).collect(Collectors.toList()));
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

    @Override
    public Object updateTopic(UpdateTopicForm updateTopicForm) {
        List<Topic> topicList = topicDao.findAll();
        List<String> topicNames = topicList.stream().map(Topic::getName).collect(Collectors.toList());
        for (int i = 0; i < topicNames.size(); i++) {
            if(topicNames.get(i).equals(updateTopicForm.getName()))
                if(!topicList.get(i).getId().equals(updateTopicForm.getId()))
                    return  "专题名称重复了，请重新提交！";
        }
        Topic topic = new Topic();
        topic.setId(updateTopicForm.getId());
        topic.setUpdatedTime(new Date());
        topic.setName(updateTopicForm.getName());
        if (updateTopicForm.getIfDeleted()!= null) {
            topic.setIfDeleted(updateTopicForm.getIfDeleted());
        }
        Topic topic1 = topicDao.save(topic);
        List<TopicKeyword> topicKeywordList = topicKeywordDao.findAllByTopicId(topic.getId());
        List<String> keywordNames = updateTopicForm.getKeywordNames();
        if (keywordNames == null) {
            for (TopicKeyword topicKeyword : topicKeywordList) {
                topicKeyword.setEnable(false);
                topicKeyword.setUpdatedTime(new Date());
                topicKeywordDao.save(topicKeyword);
            }
        }else{
            ArrayList<String> keywordNameList = new ArrayList<>();
            for (TopicKeyword topicKeyword: topicKeywordList) {
//            将所有存在的topicKeyword存入list
                keywordNameList.add(topicKeyword.getName());
//            判断这个关键词是否存在于修改的对象中
                boolean flag = keywordNames.contains(topicKeyword.getName());
                if (flag) {
//                存在则需要判断是否为失效状态
                    if (!topicKeyword.getEnable()) {
                        topicKeyword.setEnable(true);
                        topicKeyword.setUpdatedTime(new Date());
                        topicKeywordDao.save(topicKeyword);
                    }
                }else{
//                不存在则置为失效状态
                    topicKeyword.setEnable(false);
                    topicKeyword.setUpdatedTime(new Date());
                    topicKeywordDao.save(topicKeyword);
                }
            }

            for(String keywordName: keywordNames) {
//            判断是否有新增的关键词
                boolean flag = keywordNameList.contains(keywordName);
                if (!flag) {
                    TopicKeyword topicKeyword = new TopicKeyword();
                    topicKeyword.setCreatedTime(new Date());
                    topicKeyword.setName(keywordName);
                    topicKeyword.setTopicId(topic.getId());
                    TopicKeyword save = topicKeywordDao.save(topicKeyword);
                }
            }
        }
        List<TopicKeyword> topicKeywordList1 = topicKeywordDao.findAllByEnableAndTopicId(true, topic1.getId());
        TopicVO topicVO = new TopicVO(topic1);
        if (topicKeywordList1.size() > 0)
            topicVO.setKeywordNames(topicKeywordList1.stream().map(TopicKeyword::getName).collect(Collectors.toList()));
        return topicVO;
    }

    @Override
    public TopicVO recoveryTopic(Long id) {
        Topic topic = topicDao.getOne(id);
        topic.setIfDeleted(false);
        topic.setUpdatedTime(new Date());
        Topic topic1 = topicDao.save(topic);
        TopicVO topicVO = new TopicVO(topic1);
        List<TopicKeyword> keywordList = topicKeywordDao.findAllByEnableAndTopicId(true, id);
        if (keywordList.size() > 0)
            topicVO.setKeywordNames(keywordList.stream().map(TopicKeyword::getName).collect(Collectors.toList()));
        return topicVO;
    }
}
