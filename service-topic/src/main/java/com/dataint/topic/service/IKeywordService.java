package com.dataint.topic.service;

import com.dataint.topic.db.entity.Keywords;

import java.util.List;

public interface IKeywordService {
    /**
     * 根据topicId获取所有keywords
     * @param topicId
     * @return
     */
    List<Keywords> getKeywordListByTopicId(Long topicId);
}
