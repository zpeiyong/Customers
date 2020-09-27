package com.dataint.topic.service;

import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.topic.model.vo.EventBaseVO;
import com.dataint.topic.model.vo.EventVO;

public interface IEventService {

    Object getEventList(int keywordId, PageParam pageParam);

    Object addFromUser(EventVO eventVO);

    Object addFromList(EventBaseVO eventBaseVO);

    Object deleteEvent(int eventId);

}
