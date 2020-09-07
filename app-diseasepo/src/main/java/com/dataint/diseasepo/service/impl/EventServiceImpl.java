package com.dataint.diseasepo.service.impl;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.diseasepo.model.form.EventForm;
import com.dataint.diseasepo.model.form.EventUpdateForm;
import com.dataint.diseasepo.model.param.EventQueryParam;
import com.dataint.diseasepo.provider.EventProvider;
import com.dataint.diseasepo.service.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements IEventService {

    @Autowired
    private EventProvider eventProvider;

    @Override
    public ResultVO add(EventForm eventForm) {

        return eventProvider.add(eventForm);
    }

    @Override
    public ResultVO listAll(EventQueryParam eventQueryParam) {

        return eventProvider.listAll(eventQueryParam);
    }

    @Override
    public ResultVO delete(Integer eventId) {

        return eventProvider.delete(eventId);
    }

    @Override
    public ResultVO updateEnabled(Integer eventId, String enabled) {

        return eventProvider.updateEnabled(eventId, enabled);
    }

    @Override
    public ResultVO updateEvent(EventUpdateForm eventUpdateForm) {

        return eventProvider.updateEvent(eventUpdateForm);
    }
}
