package com.dataint.topic.service.impl;

import com.alibaba.fastjson.JSON;
import com.dataint.topic.db.dao.ITopicDao;
import com.dataint.topic.db.dao.ITopicKeywordDao;
import com.dataint.topic.db.entity.Topic;
import com.dataint.topic.db.entity.TopicKeyword;
import com.dataint.topic.model.vo.TopicKeywordVO;
import com.dataint.topic.model.vo.TopicTreeVO;
import com.dataint.topic.service.ITopicKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TopicKeywordServiceImpl implements ITopicKeywordService {
    @Autowired
    private ITopicKeywordDao topicKeywordDao;

    @Autowired
    private ITopicDao topicDao;

    @Override
    public boolean saveBatch(Long topicId, List<String> keywordNames) {
        List<TopicKeyword> topicKeywords = keywordNames.stream().map(names -> {
            TopicKeyword topicKeyword = new TopicKeyword();
            topicKeyword.setCreatedTime(new Date());
            topicKeyword.setTopicId(topicId);
            return topicKeyword.setName(names);
        }).collect(Collectors.toList());

        topicKeywordDao.saveAll(topicKeywords);
        return true;
    }

    @Override
    public boolean removeById(Long id) {
        topicKeywordDao.deleteById(id);
        return true;
    }

    @Override
    public List<TopicKeyword> getKeywordListByTopicId(Integer topicId) {
        List<TopicKeyword> keywordList = topicKeywordDao.findAllByEnableAndTopicId(true, topicId.longValue());
        return keywordList;
    }
}
