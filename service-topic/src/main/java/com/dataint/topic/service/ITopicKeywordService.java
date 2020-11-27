package com.dataint.topic.service;

import com.dataint.topic.db.entity.Topic;
import com.dataint.topic.db.entity.TopicKeyword;
import com.dataint.topic.model.vo.TopicKeywordVO;

import java.util.List;

public interface ITopicKeywordService {
    /**
     * 批量添加
     * @param topicId
     * @param keywordNames
     * @return
     */
    boolean saveBatch(Long topicId, List<String> keywordNames);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    boolean removeById(Long id);


    List<TopicKeyword> getKeywordListByTopicId(Integer topicId);
}
