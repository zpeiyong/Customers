package com.dataint.topic.service;

import com.dataint.topic.model.vo.TopicKeywordVO;

import java.util.List;

public interface ITopicKeywordService {
    /**
     * 批量添加
     * @param topicId
     * @param keyWordNames
     * @return
     */
    boolean saveBatch(Long topicId, List<String> keyWordNames);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    boolean removeById(Long id);

    /**
     * 获取整个列表
     * @param
     * @return
     */
    List<TopicKeywordVO> topicKeyWordList();

    /**
     * 获取整个历史列表
     * @return
     */
    List<TopicKeywordVO> delTopicList();
}
