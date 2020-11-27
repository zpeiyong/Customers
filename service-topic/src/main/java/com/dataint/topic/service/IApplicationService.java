package com.dataint.topic.service;


import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.topic.model.form.ApplyForm;
import com.dataint.topic.model.form.TopicForm;
import com.dataint.topic.model.form.UpdateTopicForm;
import com.dataint.topic.model.vo.ApplicationVO;
import com.dataint.topic.model.vo.ApplyVO;

import java.util.Map;

public interface IApplicationService {
    Object applyAddTopic(TopicForm topicForm);

    Object applyUpdateTopic(UpdateTopicForm updateTopicForm);

    Object saveApply(ApplyForm applyForm);

    ApplyVO getApplyInfo(Long id, Long topicId);

    Map<String, Object> getAllApply(Integer current, Integer pageSize, String keyword);

    Map<String, Object> getProcessedApply(Integer current, Integer pageSize, String keyword);

    ApplicationVO refuseApply(Integer id, String feedback);

    ApplicationVO applyDelTopic(Integer id);
}
