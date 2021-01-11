package com.dataint.monitor.service.impl;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.monitor.model.form.EventForm;
import com.dataint.monitor.model.form.EventUpdateForm;
import com.dataint.monitor.model.param.EventQueryParam;
import com.dataint.monitor.service.IEventService;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements IEventService {

//    @Autowired
//    private EventProvider eventProvider;

    @Override
    public ResultVO add(EventForm eventForm) {

        return null;
//        return eventProvider.add(eventForm);
    }

    @Override
    public ResultVO listAll(EventQueryParam eventQueryParam) {

        return null;
//        return eventProvider.listAll(eventQueryParam);
    }

    @Override
    public ResultVO delete(Integer eventId) {

        return null;
//        return eventProvider.delete(eventId);
    }

    @Override
    public ResultVO updateEnabled(Integer eventId, String enabled) {

        return null;
//        return eventProvider.updateEnabled(eventId, enabled);
    }

    @Override
    public ResultVO updateEvent(EventUpdateForm eventUpdateForm) {

        return null;
//        return eventProvider.updateEvent(eventUpdateForm);
    }
}
