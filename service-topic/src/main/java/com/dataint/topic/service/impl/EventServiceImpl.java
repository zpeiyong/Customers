package com.dataint.topic.service.impl;

import com.dataint.topic.db.entity.Article;
import com.dataint.topic.db.entity.Event;
import com.dataint.topic.db.entity.MediaType;
import com.dataint.topic.db.repository.ArticleRepository;
import com.dataint.topic.db.repository.EventRepository;
import com.dataint.topic.db.repository.MediaTypeRepository;
import com.dataint.topic.model.BaseRequest;
import com.dataint.topic.model.EventBaseVO;
import com.dataint.topic.model.EventVO;
import com.dataint.topic.service.IEventService;
import com.dataint.topic.utils.ResultUtil;
import com.dataint.topic.common.dim.TvtExceptionEnum;
import com.dataint.topic.common.exception.ThinventBaseException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EventServiceImpl implements IEventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MediaTypeRepository mediaTypeRepository;

    @Override
    public Object getEventList(int keywordId, BaseRequest baseRequest) throws ThinventBaseException {

        return eventRepository.getEventsByKeywordId(keywordId, PageRequest.of(
                baseRequest.getCurrentPage()-1, baseRequest.getPageSize(), Sort.by("eventTime").descending()));
    }

    @Override
    public Object addFromUser(EventVO eventVO) throws ThinventBaseException {
        Event event = new Event();

        BeanUtils.copyProperties(eventVO, event);
//        event.setEventType("0");  // '0' default for nothing
        event.setEventType(eventVO.getEventType());
        event.setGmtCreate(new Date());

        event.setMediaType(new MediaType(eventVO.getMediaTypeId()));

        eventRepository.save(event);

        return event;
    }

    @Override
    public Object addFromList(EventBaseVO eventBaseVO) throws ThinventBaseException {
        Article article = articleRepository.getOne(eventBaseVO.getArticleId());
        if (article.getEvent() != null) {
            throw new ThinventBaseException(TvtExceptionEnum.DATA_REPETITION.getIndex(), TvtExceptionEnum.DATA_REPETITION.getName());
        }

        Event event = new Event();

        // site
        MediaType mediaType = mediaTypeRepository.getMediaTypeByMediaTypeNameLike(article.getAuthor());
        if (mediaType != null)
            event.setMediaType(mediaType);

        // article
        event.setSourceName(article.getAuthor());
        event.setEventTime(article.getGmtRelease());
        event.setTitle(article.getTitle());
        event.setSummary(article.getSummary());

        // event
        event.setKeywordId(article.getKeywordId());
        event.setSubTitle(eventBaseVO.getSubTitle());
        event.setEventType(eventBaseVO.getEventType());  // '0' default for nothing
        event.setGmtCreate(new Date());

        // save event
        eventRepository.save(event);

        // save article
        article.setEvent(event);
        articleRepository.save(article);

        return ResultUtil.buildSuccResultMap();
    }

    @Override
    public Object deleteEvent(int eventId) throws ThinventBaseException {

        Event event = eventRepository.getEventByEventId(eventId);

        if (event != null) {
            Article article = articleRepository.getArticleByEvent(event);

            article.setEvent(null);
            articleRepository.save(article);
        }

        eventRepository.deleteById(eventId);

        return ResultUtil.buildSuccResultMap();
    }
}
