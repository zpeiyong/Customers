package com.dataint.topic.service.impl;

import com.dataint.topic.db.dao.ITopicDao;
import com.dataint.topic.db.entity.Topic;
import com.dataint.topic.model.vo.TopicVO;
import com.dataint.topic.service.ITopicKeywordService;
import com.dataint.topic.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Optional;

@Service
public class TopicServiceImpl implements ITopicService {
    @Autowired
    private ITopicDao topicDao;
    @Autowired
    private ITopicKeywordService topicKeywordService;
    @Override
    public TopicVO addTopic(Topic topic) {
        topic.setCreatedTime(new Date());
        topicDao.save(topic);
//        topicKeywordService.saveBatch(topic.getId(),topic.getKeywordList());
        return new TopicVO(topic);
    }

    @Override
    @Transactional
    public boolean deleteLogic(Long id) {
        Optional<Topic> topicOpt = topicDao.findById(id);
        if(topicOpt.isPresent()) {
            Topic topic = topicOpt.get();
            topic.setIfDeleted(true);
            topic.setEnable(false);
            topic.setUpdatedTime(new Date());
            topicDao.save(topic);
        } else {
            throw  new EntityNotFoundException("找不到该专题组");
        }
        return  true;
    }

    @Override
    @Transactional
    public boolean deleteReal(Long id) {
       topicKeywordService.removeById(id);
       topicDao.deleteById(id);

       return true;
    }

    @Override
    @Transactional
    public boolean update(Topic topic) {
        Optional<Topic> topicOpt = topicDao.findById(topic.getId());
        Topic ifExist = topicOpt.get();
        topic.setUpdatedTime(new Date());
        topic.setEnable(ifExist.getEnable());
        topic.setIfDeleted(ifExist.getIfDeleted());
        topic.setCreatedTime(ifExist.getCreatedTime());
        topic.setCreatedBy(ifExist.getCreatedBy());
        topic.setUpdatedBy(ifExist.getUpdatedBy());
        topicDao.save(topic);
//        topicKeywordService.saveBatch(topic.getId(),topic.getKeywordList());
        return true;
    }

}
