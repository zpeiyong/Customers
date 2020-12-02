package com.dataint.service.datapack.service;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.service.datapack.model.form.EventForm;
import com.dataint.service.datapack.model.form.EventUpdateForm;
import com.dataint.service.datapack.model.param.EventQueryParam;
import com.dataint.service.datapack.model.vo.EventVO;

public interface IEventService {

    EventVO add(EventForm eventForm);

    ResultVO listAll(EventQueryParam eventQueryParam);

    boolean delete(Long eventId);

    EventVO updateEnabled(Long eventId, String enabled);

    EventVO updateEvent(EventUpdateForm eventUpdateForm);

}
