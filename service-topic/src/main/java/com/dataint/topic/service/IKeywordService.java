package com.dataint.topic.service;

import com.dataint.topic.model.vo.KeywordVO;

import java.util.List;

public interface IKeywordService {
    /**
     * 根据topicId获取所有keywords
     * @param topicId
     * @return
     */
    List<KeywordVO> getKeywordListByTopicId(Long topicId);

    /**
     * 根据疾病名称找关键字
     * @param diseas
     * @return
     */
    List<KeywordVO> getKeywordListByDiease(String diseas);
}
