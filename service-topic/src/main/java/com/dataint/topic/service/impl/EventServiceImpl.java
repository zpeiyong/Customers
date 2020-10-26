package com.dataint.topic.service.impl;

import com.dataint.cloud.common.dim.BaseExceptionEnum;
import com.dataint.cloud.common.exception.DataintBaseException;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.topic.db.dao.IArticleDao;
import com.dataint.topic.db.dao.IEventDao;
import com.dataint.topic.db.dao.IMediaTypeDao;
import com.dataint.topic.db.entity.MediaType;
import com.dataint.topic.db.entity.Topic;
import com.dataint.topic.db.entity.TopicArticle;
import com.dataint.topic.model.vo.EventBaseVO;
import com.dataint.topic.model.vo.EventVO;
import com.dataint.topic.service.IEventService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements IEventService {
    @Autowired
    private IEventDao eventDao;

    @Autowired
    private IArticleDao articleDao;

    @Autowired
    private IMediaTypeDao mediaTypeDao;

    @Override
    public Object getEventList(int keywordId, PageParam pageParam) {

//        return eventDao.getEventsByKeywordId(keywordId, PageRequest.of(
//                pageParam.getCurrent()-1, pageParam.getPageSize(), Sort.by("eventTime").descending()));
        return null;
    }

    @Override
    public Object addFromUser(EventVO eventVO) {
        Topic event = new Topic();

        BeanUtils.copyProperties(eventVO, event);
//        event.setEventType("0");  // '0' default for nothing
//        event.setEventType(eventVO.getEventType());
//        event.setGmtCreate(new Date());
//
//        event.setMediaType(new MediaType(eventVO.getMediaTypeId()));

//      eventDao.save(event);

        return event;
    }

    @Override
    public Object addFromList(EventBaseVO eventBaseVO) {
        TopicArticle article = articleDao.getOne(eventBaseVO.getArticleId());
        if (article.getTopicId() != null) {
            throw new DataintBaseException(BaseExceptionEnum.DATA_REPETITION.getName(), BaseExceptionEnum.DATA_REPETITION.getIndex());
        }

        Topic event = new Topic();

        // site
        MediaType mediaType = mediaTypeDao.getMediaTypeByNameLike(article.getAuthor());
        if (mediaType != null)
//            event.setMediaType(mediaType);

//        // article
//        event.setSourceName(article.getAuthor());
//        event.setEventTime(article.getGmtRelease());
//        event.setTitle(article.getTitle());
//        event.setSummary(article.getSummary());
//
//        // event
//        event.setKeywordId(article.getKeywordId());
//        event.setSubTitle(eventBaseVO.getSubTitle());
//        event.setEventType(eventBaseVO.getEventType());  // '0' default for nothing
//        event.setGmtCreate(new Date());

        // save event
 //           eventDao.save(event);

        // save article
//        article.setEvent(event);
        articleDao.save(article);

        return null;
    }

    @Override
    public Object deleteEvent(int eventId) {

        Topic event = eventDao.getEventByEventId(eventId);

//        if (event != null) {
//            TopicArticle article = articleDao.getArticleByEvent(event);
//
//            article.setTopicId(null);
//            articleDao.save(article);
//        }

        eventDao.deleteById(eventId);

//        return ResultUtil.buildSuccResultMap();
        return null;
    }
}
