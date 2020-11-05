package com.dataint.topic.service;

import com.dataint.topic.db.entity.Topic;
import com.dataint.topic.model.vo.TopicVO;

public interface ITopicService {
    /**
     * 新增专题组
     * @param topic
     * @return
     */
      TopicVO addTopic(Topic topic);

    /**
     * 逻辑删除
     * @param id
     */
    boolean deleteLogic(Long id);

    /**
     * 根据id真实删除
     * @param id
     */
    boolean deleteReal(Long id);

    /**
     * 修改专题组
     * @param topic
     * @return
     */
        boolean update(Topic topic);
}
