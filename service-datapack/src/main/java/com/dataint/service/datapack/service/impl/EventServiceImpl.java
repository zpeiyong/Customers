package com.dataint.service.datapack.service.impl;

import com.dataint.cloud.common.exception.DataNotExistException;
import com.dataint.cloud.common.exception.DataintBaseException;
import com.dataint.cloud.common.model.Constants;
import com.dataint.cloud.common.model.Pagination;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.model.po.BasePO;
import com.dataint.service.datapack.dao.ICountryDao;
import com.dataint.service.datapack.dao.IEventDao;
import com.dataint.service.datapack.dao.IEventSourceDao;
import com.dataint.service.datapack.dao.ISourceDao;
import com.dataint.service.datapack.dao.entity.Country;
import com.dataint.service.datapack.dao.entity.Event;
import com.dataint.service.datapack.dao.entity.EventSource;
import com.dataint.service.datapack.dao.entity.Source;
import com.dataint.service.datapack.model.EventVO;
import com.dataint.service.datapack.model.SourceVO;
import com.dataint.service.datapack.model.form.EventForm;
import com.dataint.service.datapack.model.form.EventUpdateForm;
import com.dataint.service.datapack.model.form.SourceForm;
import com.dataint.service.datapack.model.form.SourceUpdateForm;
import com.dataint.service.datapack.model.param.EventQueryParam;
import com.dataint.service.datapack.service.IEventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventServiceImpl implements IEventService {

    @Autowired
    private IEventDao eventDao;

    @Autowired
    private IEventSourceDao eventSourceDao;

    @Autowired
    private ISourceDao sourceDao;

    @Autowired
    private ICountryDao countryDao;

    @Transactional
    @Override
    public EventVO add(EventForm eventForm) {
        Event event = eventForm.toPo(Event.class);

        // 疫情事件开始/结束时间
        try {
            if (!StringUtils.isEmpty(eventForm.getEventStart()))
                event.setEventStart(Constants.DateSDF.parse(eventForm.getEventStart()));
            if (!StringUtils.isEmpty(eventForm.getEventEnd()))
                event.setEventEnd(Constants.DateSDF.parse(eventForm.getEventEnd()));
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        event.setCreatedTime(new Date());
        eventDao.save(event);

        // 疫情事件对应数据来源
        List<SourceForm> sourceFormList = eventForm.getSourceList();
        List<Integer> sourceIdList = null;
        if (!CollectionUtils.isEmpty(sourceFormList)) {
            // persist source
            List<Source> sourceList = sourceFormList.stream().map(sourceForm -> {
                Country country = countryDao.getOne(sourceForm.getCountryId());
                Source source = sourceForm.toPo(Source.class);
                source.setCountry(country);
                source.setSourceStatus("Y");
                source.setCreatedTime(new Date());

                return source;
            }).collect(Collectors.toList());
            sourceDao.saveAll(sourceList);

            // persist event_source
            sourceIdList = sourceList.stream().map(BasePO::getId).collect(Collectors.toList());
            List<EventSource> eventSourceList = sourceIdList.stream()
                    .map(integer -> {
                        EventSource eventSource = new EventSource(event.getId(), integer);
                        eventSource.setCreatedTime(new Date());

                        return eventSource;
                    }).collect(Collectors.toList());
            eventSourceDao.saveAll(eventSourceList);
        }

        return map2EventVO(event, sourceIdList);
    }

    @Override
    public ResultVO listAll(EventQueryParam eventQueryParam) {
        Page<Event> rstPage = eventDao.findAll(new Specification<Event>() {
            @Override
            public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotEmpty(eventQueryParam.getKeyword())) {
                    list.add(criteriaBuilder.like(root.get("eventName").as(String.class), "%"+eventQueryParam.getKeyword()+"%"));
                }
                if (ObjectUtils.isNotEmpty(eventQueryParam.getDiseaseId())) {
                    list.add(criteriaBuilder.equal(root.get("diseaseId").as(String.class), eventQueryParam.getDiseaseId()));
                }
                list.add(criteriaBuilder.equal(root.get("deleted").as(String.class), "N"));

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, PageRequest.of(eventQueryParam.getCurrent() - 1, eventQueryParam.getPageSize(),
                new Sort(Sort.Direction.ASC, "enabled")));

        List<EventVO> eventVOList = rstPage.getContent().stream()
                .map(event -> map2EventVO(event, null))
                .collect(Collectors.toList());

        Pagination pagination = new Pagination(eventQueryParam.getPageSize(), rstPage.getTotalElements(), eventQueryParam.getCurrent());

        return ResultVO.success(eventVOList, pagination);
    }

    @Transactional
    @Override
    public boolean delete(Integer eventId) {
        // check if event exist
        Optional<Event> eventOpt = eventDao.findById(eventId);
        if (!eventOpt.isPresent()) {
            throw new DataNotExistException();
        }
        Event event = eventOpt.get();
        event.setDeleted("Y");
        eventDao.save(event);

        return true;
    }

    @Transactional
    @Override
    public EventVO updateEnabled(Integer eventId, String enabled) {
        // verify enabled value
        if (StringUtils.isEmpty(enabled)) {
            throw new DataintBaseException("enabled值有误!", 505);
        }
        enabled = enabled.toUpperCase();
        if (!"Y".equals(enabled) && !"N".equals(enabled)) {
            throw new DataintBaseException("enabled值有误!", 505);
        }

        // check if event exist
        Optional<Event> eventOpt = eventDao.findById(eventId);
        if (!eventOpt.isPresent()) {
            throw new DataNotExistException();
        }
        Event event = eventOpt.get();
        event.setEnabled(enabled);
        eventDao.save(event);

        return map2EventVO(event, null);
    }

    @Transactional
    @Override
    public EventVO updateEvent(EventUpdateForm eventUpdateForm) {
        // check if event exist
        Optional<Event> eventOpt = eventDao.findById(eventUpdateForm.getEventId());
        if (!eventOpt.isPresent()) {
            throw new DataNotExistException();
        }
        Event event = eventOpt.get();

        event = eventUpdateForm.toPo(event.getId(), Event.class);
        // 疫情事件开始/结束时间
        try {
            if (!StringUtils.isEmpty(eventUpdateForm.getEventStart()))
                event.setEventStart(Constants.DateSDF.parse(eventUpdateForm.getEventStart()));
            if (!StringUtils.isEmpty(eventUpdateForm.getEventEnd()))
                event.setEventEnd(Constants.DateSDF.parse(eventUpdateForm.getEventEnd()));
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        event.setUpdatedTime(new Date());
        eventDao.save(event);

        // 疫情事件对应数据来源
        List<SourceUpdateForm> updateFormList = eventUpdateForm.getSourceList();
        List<Integer> sourceIdList = null;
        if (!CollectionUtils.isEmpty(updateFormList)) {
            // persist source
            List<Source> sourceList = updateFormList.stream().map(sourceUpdateForm -> {
                Country country = countryDao.getOne(sourceUpdateForm.getCountryId());
                Source source = null;
                // update
                if (sourceUpdateForm.getSourceId() != null) {
                    // check if source exist
                    Optional<Source> sourceOpt = sourceDao.findById(sourceUpdateForm.getSourceId());
                    if (!sourceOpt.isPresent()) {
                        return null;
                    }
                    source = sourceOpt.get();

                    if (StringUtils.isNotEmpty(sourceUpdateForm.getSourceUrl()))
                        source.setSourceUrl(sourceUpdateForm.getSourceUrl());
                    if (StringUtils.isNotEmpty(sourceUpdateForm.getDescription()))
                        source.setDescription(sourceUpdateForm.getDescription());
                    source.setUpdatedTime(new Date());
                } else {
                    source = sourceUpdateForm.toPo(Source.class);
                    source.setSourceStatus("Y");
                    source.setCreatedTime(new Date());
                }
                source.setCountry(country);

                return source;
            }).collect(Collectors.toList());
            sourceDao.saveAll(sourceList);

            // persist event_source
            sourceIdList = sourceList.stream().map(BasePO::getId).collect(Collectors.toList());
            List<EventSource> eventSourceList = new ArrayList<>();
            for (Integer sourceId : sourceIdList) {
                EventSource eventSource = new EventSource(event.getId(), sourceId);
                eventSource.setUpdatedTime(new Date());
                eventSourceList.add(eventSource);
            }
            eventSourceDao.deleteByEventId(event.getId());
            eventSourceDao.saveAll(eventSourceList);
        } else
            eventSourceDao.deleteByEventId(event.getId());

        return map2EventVO(event, sourceIdList);
    }

    /**
     * 转换为EventVO
     *
     * @param event
     * @param sourceIdList
     * @return
     */
    private EventVO map2EventVO(Event event, List<Integer> sourceIdList) {
        EventVO eventVO = new EventVO(event);
        // 若未提供sourceIdList, 则先获取对应来源id列表
        if (CollectionUtils.isEmpty(sourceIdList)) {
            List<EventSource> eventSourceList = eventSourceDao.findAllByEventId(event.getId());
            sourceIdList = eventSourceList.stream().map(EventSource::getSourceId).collect(Collectors.toList());
        }

        List<Source> sourceList = sourceDao.findAllById(sourceIdList);
        eventVO.setSourceVOList(sourceList.stream().map(SourceVO::new).collect(Collectors.toList()));

        return eventVO;
    }
}
