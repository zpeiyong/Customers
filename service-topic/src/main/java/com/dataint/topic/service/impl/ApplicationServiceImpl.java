package com.dataint.topic.service.impl;

import com.dataint.cloud.common.exception.DataNotExistException;
import com.dataint.cloud.common.model.Pagination;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.topic.db.dao.IApplicationDao;
import com.dataint.topic.db.dao.ITopicDao;
import com.dataint.topic.db.entity.Application;
import com.dataint.topic.db.entity.Topic;
import com.dataint.topic.model.form.ApplyForm;
import com.dataint.topic.model.form.TopicForm;
import com.dataint.topic.model.form.UpdateTopicForm;
import com.dataint.topic.model.vo.ApplicationVO;
import com.dataint.topic.model.vo.ApplyVO;
import com.dataint.topic.model.vo.TopicVO;
import com.dataint.topic.service.IApplicationService;
import com.dataint.topic.service.ITopicService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements IApplicationService {

    @Autowired
    private IApplicationDao applicationDao;

    @Autowired
    private ITopicDao topicDao;

    @Autowired
    private ITopicService topicService;

    @Override
    public ApplicationVO applyAddTopic(TopicForm topicForm) {
        Application application = new Application();
        application.setTopicName(topicForm.getName());
        if (topicForm.getKeywordNames() != null)
            application.setKeywords(StringUtils.join(topicForm.getKeywordNames(),"|"));
        if (topicForm.getUsername() != null)
            application.setCreatedBy(topicForm.getUsername());
        application.setOperation("add");
        applicationDao.save(application);

        return new ApplicationVO(application);
    }

    @Override
    public ApplicationVO applyUpdateTopic(UpdateTopicForm updateTopicForm) {
        Application application = new Application();
        if (updateTopicForm.getIfDeleted() != null) {
            if (updateTopicForm.getIfDeleted()) {
                application.setOperation("delete");
            } else {
                application.setOperation("update");
            }
        } else {
            application.setOperation("update");
            if (updateTopicForm.getKeywordNames() != null)
                application.setKeywords(StringUtils.join(updateTopicForm.getKeywordNames(),"|"));
        }
        application.setTopicId(updateTopicForm.getId());
        application.setTopicName(updateTopicForm.getName());
        if (updateTopicForm.getUpdateDesc() != null)
            application.setUpdateDesc(updateTopicForm.getUpdateDesc());
        applicationDao.save(application);

        return new ApplicationVO(application);
    }

    @Override
    public TopicVO saveApply(ApplyForm applyForm) {
        TopicVO topicVO;

        // 判断是新增专题还是修改专题
        if (applyForm.getId() == 0) {
            // 新增专题
            TopicForm topicForm = new TopicForm();
            topicForm.setName(applyForm.getName());
            topicForm.setKeywordNames(applyForm.getKeywordNames());
            topicVO = topicService.addTopic(topicForm);

            // 维护申请表
            if (!CollectionUtils.isEmpty(applyForm.getApplicationList())) {
                Application application = applyForm.getApplicationList().get(0);
                application.setStatus(1);
                application.setTopicId(topicVO.getId());
                application.setUpdatedTime(new Date());
                applicationDao.save(application);
            }
        } else {
            UpdateTopicForm updateTopicForm = new UpdateTopicForm();
            updateTopicForm.setId(applyForm.getId());
            updateTopicForm.setName(applyForm.getName());
            updateTopicForm.setKeywordNames(applyForm.getKeywordNames());
            updateTopicForm.setIfDeleted(applyForm.getIfDeleted());
            updateTopicForm.setKeywordNames(applyForm.getKeywordNames());

            topicVO = topicService.updateTopic(updateTopicForm);

            // 维护申请表
            if (!CollectionUtils.isEmpty(applyForm.getApplicationList())) {
                applyForm.getApplicationList().forEach(application -> {
                    application.setStatus(1);
                    application.setUpdatedTime(new Date());
                    applicationDao.save(application);
                });
            }
        }

        return topicVO;
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
            List<Application> applicationList = applicationDao.findAllByTopicIdAndStatus(topicId, 0);
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
    public ResultVO getAllApply(Integer current, Integer pageSize, String keyword) {
        Page<Application> applicationPage = applicationDao.findAllByStatusAndTopicNameContainingOrderByCreatedTimeDesc(0, keyword, PageRequest.of(current - 1, pageSize));

        Pagination pagination = new Pagination();
        pagination.setCurrent(current);
        pagination.setPageSize(pageSize);
        pagination.setTotal(applicationPage.getTotalElements());

        return ResultVO.success(applicationPage.getContent(), pagination);
    }

    @Override
    public ResultVO getProcessedApply(Integer current, Integer pageSize, String keyword) {
        Page<Application> applicationPage = applicationDao.findAllByStatusIsNotAndTopicNameContainingOrderByUpdatedTimeDesc(0, keyword, PageRequest.of(current - 1, pageSize));

        Pagination pagination = new Pagination();
        pagination.setCurrent(current);
        pagination.setPageSize(pageSize);
        pagination.setTotal(applicationPage.getTotalElements());

        return ResultVO.success(applicationPage.getContent(), pagination);
    }

    @Override
    public ApplicationVO refuseApply(Long id, String feedback) {
        // check if application exist
        Optional<Application> applicationOpt = applicationDao.findById(id);
        if (!applicationOpt.isPresent()) {
            throw new DataNotExistException("申请不存在!");
        }
        Application application = applicationOpt.get();

        //
        application.setFeedback(feedback);
        application.setUpdatedTime(new Date());
        application.setStatus(2);
        applicationDao.save(application);

        return new ApplicationVO(application);
    }

    @Override
    public ApplicationVO applyDelTopic(Long id) {
        // check if topic exist
        Optional<Topic> topicOpt = topicDao.findById(id);
        if (!topicOpt.isPresent()) {
            throw new DataNotExistException("专题不存在!");
        }
        Topic topic = topicOpt.get();

        //
        Application application = new Application();
        application.setTopicId(id);
        application.setTopicName(topic.getName());
        if (topic.getIfDeleted()) {
            application.setOperation("update");
            application.setUpdateDesc("恢复专题");
        } else {
            application.setOperation("delete");
            application.setUpdateDesc("删除专题");
        }
        applicationDao.save(application);

        return new ApplicationVO(application);
    }
}
