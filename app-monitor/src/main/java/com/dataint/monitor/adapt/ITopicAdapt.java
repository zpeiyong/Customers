package com.dataint.monitor.adapt;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.monitor.model.form.TopicForm;
import com.dataint.monitor.model.form.UpdateTopicForm;

public interface ITopicAdapt {

    Object addTopic(TopicForm topicForm);

    Object delTopic(Long id);

    Object recoveryTopic(Long id);

    Object updateTopic(UpdateTopicForm updateTopicForm);

    Object getAllActiveTopic();

    Object getAllDeleteTopic();

    Object getAllTopic(Integer current, Integer pageSize, String keyword);
}
