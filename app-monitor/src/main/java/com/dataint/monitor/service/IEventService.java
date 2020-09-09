package com.dataint.monitor.service;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.monitor.model.form.EventForm;
import com.dataint.monitor.model.form.EventUpdateForm;
import com.dataint.monitor.model.param.EventQueryParam;

public interface IEventService {

    ResultVO add(EventForm eventForm);

    ResultVO listAll(EventQueryParam eventQueryParam);

    ResultVO delete(Integer eventId);

    ResultVO updateEnabled(Integer eventId, String enabled);

    ResultVO updateEvent(EventUpdateForm eventUpdateForm);
}
