package com.dataint.topic.service.impl;

import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.topic.db.entity.MediaType;
import com.dataint.topic.db.entity.Topic;
import com.dataint.topic.db.entity.TopicArticle;
import com.dataint.topic.db.repository.ArticleRepository;
import com.dataint.topic.db.repository.EventRepository;
import com.dataint.topic.db.repository.MediaTypeRepository;
import com.dataint.topic.model.EventBaseVO;
import com.dataint.topic.model.EventVO;
import com.dataint.topic.service.IEventService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements IEventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MediaTypeRepository mediaTypeRepository;

    @Override
    public Object getEventList(int keywordId, PageParam pageParam) {

        return eventRepository.getEventsByKeywordId(keywordId, PageRequest.of(
                pageParam.getCurrent()-1, pageParam.getPageSize(), Sort.by("eventTime").descending()));
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

        eventRepository.save(event);

        return event;
    }

    @Override
    public Object addFromList(EventBaseVO eventBaseVO) {
        TopicArticle article = articleRepository.getOne(eventBaseVO.getArticleId());
        if (article.getEvent() != null) {
//            throw new DataintBaseException(BaseExceptionEnum.DATA_REPETITION.getIndex(), BaseExceptionEnum.DATA_REPETITION.getName());
        }

        Topic event = new Topic();

        // site
        MediaType mediaType = mediaTypeRepository.getMediaTypeByMediaTypeNameLike(article.getAuthor());
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
        eventRepository.save(event);

        // save article
//        article.setEvent(event);
        articleRepository.save(article);

        return null;
    }

    @Override
    public Object deleteEvent(int eventId) {

        Topic event = eventRepository.getEventByEventId(eventId);

        if (event != null) {
            TopicArticle article = articleRepository.getArticleByEvent(event);

            article.setEvent(null);
            articleRepository.save(article);
        }

        eventRepository.deleteById(eventId);

//        return ResultUtil.buildSuccResultMap();
        return null;
    }
}
