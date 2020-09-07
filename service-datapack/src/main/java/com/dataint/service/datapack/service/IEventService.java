package com.dataint.service.datapack.service;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.service.datapack.model.EventVO;
import com.dataint.service.datapack.model.form.EventForm;
import com.dataint.service.datapack.model.form.EventUpdateForm;
import com.dataint.service.datapack.model.param.EventQueryParam;

public interface IEventService {

    EventVO add(EventForm eventForm);

    ResultVO listAll(EventQueryParam eventQueryParam);

    boolean delete(Integer eventId);

    EventVO updateEnabled(Integer eventId, String enabled);

    EventVO updateEvent(EventUpdateForm eventUpdateForm);

}
