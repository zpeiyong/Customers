package com.dataint.topic.service;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.topic.db.entity.Topic;
import com.dataint.topic.model.form.TopicForm;
import com.dataint.topic.model.form.UpdateTopicForm;
import com.dataint.topic.model.vo.TopicVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ITopicService {
    /**
     * 新增专题组
     * @param topicForm
     * @return
     */
    TopicVO addTopic(TopicForm topicForm);

    /**
     * 获取所有专题组
     * @return
     */
    ResultVO getAllTopic(Integer current, Integer pageSize, String keyword);

    /**
     * 删除专题
     * @param id
     * @return
     */
    TopicVO delTopic(Long id);

    /**
     * 获取激活专题组
     * @return
     */
    List<Topic> getAllActiveTopic();

    /**
     * 获取历史专题组
     * @return
     */
    List<Topic> getAllDeleteTopic();

    /**
     * 修改专题组
     * @param updateTopicForm
     * @return
     */
    TopicVO updateTopic(UpdateTopicForm updateTopicForm);

    /**
     *
     * @param id
     * @return
     */
    TopicVO recoveryTopic(Long id);

    List<Map<String,Object>> getPopularFeelingsTj(String gmtDate);
}
