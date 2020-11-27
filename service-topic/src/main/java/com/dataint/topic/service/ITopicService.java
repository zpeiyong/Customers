package com.dataint.topic.service;

import com.dataint.topic.db.entity.Topic;
import com.dataint.topic.model.form.TopicForm;
import com.dataint.topic.model.form.UpdateTopicForm;
import com.dataint.topic.model.vo.TopicVO;

import java.util.List;
import java.util.Map;

public interface ITopicService {
    /**
     * 新增专题组
     * @param topicForm
     * @return
     */
      Object addTopic(TopicForm topicForm);

    /**
     * 获取整个专题列表
     * @return
     */
    Map<String, Object> getAllTopic(Integer current, Integer pageSize, String keyword);

    /**
     * 删除专题
     * @param id
     * @return
     */

    TopicVO delTopic(Long id);

    List<Topic> getAllActiveTopic();


    /**
     * 获取整个历史列表
     * @return
     */
    List<Topic> getAllDeleteTopic();

    /**
     * 修改专题组
     * @param updateTopicForm
     * @return
     */
    Object updateTopic(UpdateTopicForm updateTopicForm);

    TopicVO recoveryTopic(Long id);
}
