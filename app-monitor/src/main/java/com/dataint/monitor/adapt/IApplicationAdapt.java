package com.dataint.monitor.adapt;


import com.dataint.monitor.model.form.ApplyForm;
import com.dataint.monitor.model.form.TopicForm;
import com.dataint.monitor.model.form.UpdateTopicForm;

public interface IApplicationAdapt {
    Object applyAddTopic(TopicForm topicForm);

    Object applyUpdateTopic(UpdateTopicForm updateTopicForm);

    Object getAllApply(Integer current, Integer pageSize, String keyword);

    Object getProcessedApply(Integer current, Integer pageSize, String keyword);

    Object getApplyInfo(Long id, Long topicId);

    Object saveApply(ApplyForm applyForm);

    Object refuseApply(Integer id, String feedback);

    Object applyDelTopic(Integer id);
}
