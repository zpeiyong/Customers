package com.dataint.topic.service.impl;

import com.dataint.cloud.common.model.Pagination;
import com.dataint.topic.db.dao.IApplicationDao;
import com.dataint.topic.db.dao.ITopicDao;
import com.dataint.topic.db.dao.ITopicKeywordDao;
import com.dataint.topic.db.entity.Application;
import com.dataint.topic.db.entity.Topic;
import com.dataint.topic.db.entity.TopicKeyword;
import com.dataint.topic.model.form.ApplyForm;
import com.dataint.topic.model.form.TopicForm;
import com.dataint.topic.model.form.UpdateTopicForm;
import com.dataint.topic.model.vo.ApplicationVO;
import com.dataint.topic.model.vo.ApplyVO;
import com.dataint.topic.service.IApplicationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements IApplicationService {

    @Autowired
    private IApplicationDao applicationDao;

    @Autowired
    private ITopicDao topicDao;

    @Autowired
    private ITopicKeywordDao topicKeywordDao;

    @Override
    public Object applyAddTopic(TopicForm topicForm) {
        Application application = new Application();
        application.setTopicName(topicForm.getName());
        if (topicForm.getKeywordNames() != null)
            application.setKeywords(StringUtils.join(topicForm.getKeywordNames(),"|"));
        application.setOperation("add");
        Application save = applicationDao.save(application);
        return save;
    }

    @Override
    public Object applyUpdateTopic(UpdateTopicForm updateTopicForm) {
        Application application = new Application();
        if (updateTopicForm.getIfDeleted() != null) {
            if (updateTopicForm.getIfDeleted()) {
                application.setOperation("delete");
            }else {
                application.setOperation("update");
            }
        }else{
            application.setOperation("update");
            if (updateTopicForm.getKeywordNames() != null)
                application.setKeywords(StringUtils.join(updateTopicForm.getKeywordNames(),"|"));
        }
        application.setTopicId((int) updateTopicForm.getId());
        application.setTopicName(updateTopicForm.getName());
        if (updateTopicForm.getUpdateDesc() != null)
            application.setUpdateDesc(updateTopicForm.getUpdateDesc());
        return applicationDao.save(application);
    }

    @Override
    public Object saveApply(ApplyForm applyForm) {
        //判断专题名称是否已经存在
        List<Topic> topicList = topicDao.findAll();
        boolean flag = false;
        for (Topic topic: topicList) {
            if (topic.getName().equals(applyForm.getName())){
                if (applyForm.getId() == 0)
                    flag = true;
                else if (!topic.getId().equals(applyForm.getId()))
                    flag = true;
            }
        }
        if (flag) {
            return "专题名称重复了，请重新提交！";
        }
        //判断是新增专题还是修改专题
        if (applyForm.getId() == 0) {
            // 新增专题
            Topic topic = new Topic();
            topic.setName(applyForm.getName());
            topic.setIfDeleted(applyForm.getIfDeleted());
            topic.setEnable(true);
            Topic topic1 = topicDao.save(topic);
            if (applyForm.getKeywordNames() != null && applyForm.getKeywordNames().size() > 0) {
               for (String keywordName: applyForm.getKeywordNames()) {
                   TopicKeyword topicKeyword = new TopicKeyword();
                   topicKeyword.setEnable(true);
                   topicKeyword.setTopicId(topic1.getId());
                   topicKeyword.setName(keywordName);
                   topicKeywordDao.save(topicKeyword);
               }
            }
            if (applyForm.getApplicationList() != null && applyForm.getApplicationList().size() > 0){
                Application application = applyForm.getApplicationList().get(0);
                application.setStatus(1);
                application.setTopicId(topic1.getId().intValue());
                application.setUpdatedTime(new Date());
                applicationDao.save(application);
            }
            return topic1;
        }else{
            Topic topic = topicDao.findById(applyForm.getId()).get();
            topic.setIfDeleted(applyForm.getIfDeleted());
            topic.setName(applyForm.getName());
            topic.setUpdatedTime(new Date());
            Topic topic1 = topicDao.save(topic);
            List<TopicKeyword> topicKeywordList = topicKeywordDao.findAllByTopicId(topic1.getId());
            if (applyForm.getKeywordNames() != null && applyForm.getKeywordNames().size() > 0) {
                for (TopicKeyword topicKeyword: topicKeywordList) {
                    boolean boo = applyForm.getKeywordNames().contains(topicKeyword.getName());
                    if (boo) {
                        topicKeyword.setEnable(true);
                    }else{
                        topicKeyword.setEnable(false);
                    }
                    topicKeywordDao.save(topicKeyword);
                }
                List<String> list = new ArrayList<>();
                for (TopicKeyword topicKeyword : topicKeywordList) {
                    String name = topicKeyword.getName();
                    list.add(name);
                }
                for(String keywordName:applyForm.getKeywordNames()){
                    int i = list.indexOf(keywordName);
                    if (i == -1) {
                        TopicKeyword topicKeyword = new TopicKeyword();
                        topicKeyword.setEnable(true);
                        topicKeyword.setTopicId(topic1.getId());
                        topicKeyword.setName(keywordName);
                        topicKeywordDao.save(topicKeyword);
                    }else{
                        TopicKeyword topicKeyword = topicKeywordList.get(i);
                        topicKeyword.setEnable(true);
                        topicKeywordDao.save(topicKeyword);
                    }
                }
            }else{
                for (TopicKeyword topicKeyword: topicKeywordList) {
                    topicKeyword.setEnable(false);
                    topicKeywordDao.save(topicKeyword);
                }
            }
            if (applyForm.getApplicationList() != null && applyForm.getApplicationList().size() > 0){
                applyForm.getApplicationList().forEach(application -> {
                    if (topic1.getKeywordList() != null && topic1.getKeywordList().size() > 0)
                        application.setKeywords(StringUtils.join(topic1.getKeywordList(),"|"));
                    application.setStatus(1);
                    application.setUpdatedTime(new Date());
                    applicationDao.save(application);
                });
            }
            return  topic1;
        }
    }

    @Override
    public ApplyVO getApplyInfo(Long id, Long topicId) {
        Application application = applicationDao.getOne(id);
        ApplyVO applyVO = new ApplyVO();
        applyVO.setId(topicId);
        applyVO.setName(application.getTopicName());
        if (application.getKeywords() != null && application.getKeywords().length() > 0)
            applyVO.setKeywordNames(Arrays.asList(application.getKeywords().split("\\|")));
        if (topicId == 0) {
            applyVO.setIfDeleted(false);
            applyVO.setApplicationList((Arrays.asList(new ApplicationVO(application))));
        }else{
            List<Application> applicationList = applicationDao.findAllByTopicIdAndStatus(topicId.intValue(), 0);
            Topic topic = topicDao.getOne(topicId);
            if (applicationList.size() > 0) {
                List<ApplicationVO> voList = applicationList.stream().map(ApplicationVO::new).collect(Collectors.toList());
                applyVO.setApplicationList(voList);
            }
            applyVO.setIfDeleted(topic.getIfDeleted());
        }
        return applyVO;
    }


    @Override
    public Map<String, Object> getAllApply(Integer current, Integer pageSize, String keyword) {
        Page<Application> applicationPage = applicationDao.findAllByStatusAndTopicNameContainingOrderByCreatedTimeDesc(0, keyword, PageRequest.of(current - 1, pageSize));
        return getStringObjectMap(current, pageSize, applicationPage);
    }

    @Override
    public Map<String, Object> getProcessedApply(Integer current, Integer pageSize, String keyword) {
        Page<Application> applicationPage = applicationDao.findAllByStatusIsNotAndTopicNameContainingOrderByUpdatedTimeDesc(0, keyword, PageRequest.of(current - 1, pageSize));
        return getStringObjectMap(current, pageSize, applicationPage);
    }

    @Override
    public ApplicationVO refuseApply(Integer id, String feedback) {
        Application application = applicationDao.findById((long) id).get();
        application.setFeedback(feedback);
        application.setUpdatedTime(new Date());
        application.setStatus(2);
        Application save = applicationDao.save(application);
        return new ApplicationVO(save);
    }

    @Override
    public ApplicationVO applyDelTopic(Integer id) {
        Topic topic = topicDao.findById((long)id).get();
        Application application = new Application();
        application.setTopicId(id);
        application.setTopicName(topic.getName());
        if (topic.getIfDeleted()) {
            application.setOperation("update");
            application.setUpdateDesc("恢复专题");
        }
        else {
            application.setOperation("delete");
            application.setUpdateDesc("删除专题");
        }
        Application save = applicationDao.save(application);
        ApplicationVO applicationVO = new ApplicationVO(save);
        return applicationVO;
    }

    private Map<String, Object> getStringObjectMap(Integer current, Integer pageSize, Page<Application> applicationPage) {
        List<ApplicationVO> applicationVOList = applicationPage.getContent().stream().map(ApplicationVO::new).collect(Collectors.toList());
        Pagination pagination = new Pagination();
        pagination.setCurrent(current);
        pagination.setPageSize(pageSize);
        pagination.setTotal(applicationPage.getTotalElements());
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("list", applicationVOList);
        stringObjectMap.put("pagination", pagination);
        return stringObjectMap;
    }
}
