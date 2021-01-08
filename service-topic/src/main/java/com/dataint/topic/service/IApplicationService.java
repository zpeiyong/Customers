package com.dataint.topic.service;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.topic.model.form.ApplyForm;
import com.dataint.topic.model.form.TopicForm;
import com.dataint.topic.model.form.UpdateTopicForm;
import com.dataint.topic.model.vo.ApplicationVO;
import com.dataint.topic.model.vo.ApplyVO;
import com.dataint.topic.model.vo.TopicVO;

public interface IApplicationService {
    ApplicationVO applyAddTopic(TopicForm topicForm);

    ApplicationVO applyUpdateTopic(UpdateTopicForm updateTopicForm);

    TopicVO saveApply(ApplyForm applyForm);

    ApplyVO getApplyInfo(Long id, Long topicId);

    ResultVO getAllApply(Integer current, Integer pageSize, String keyword);

    ResultVO getProcessedApply(Integer current, Integer pageSize, String keyword);

    ApplicationVO refuseApply(Long id, String feedback);

    ApplicationVO applyDelTopic(Long id);
}
