package com.dataint.topic.service.impl;

import com.dataint.topic.db.dao.IKeywordDao;
import com.dataint.topic.db.entity.Keywords;
import com.dataint.topic.service.IKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordServiceImpl implements IKeywordService {
    
    @Autowired
    private IKeywordDao keywordDao;

    @Override
    public List<Keywords> getKeywordListByTopicId(Long topicId) {
        List<Keywords> keywordsList = keywordDao.findAllByTopicId(topicId);
        
        return keywordsList;
    }
}
