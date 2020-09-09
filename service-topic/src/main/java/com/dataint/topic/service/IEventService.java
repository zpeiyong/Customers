package com.dataint.topic.service;

import com.dataint.topic.model.BaseRequest;
import com.dataint.topic.model.EventBaseVO;
import com.dataint.topic.model.EventVO;
import com.dataint.topic.common.exception.ThinventBaseException;

public interface IEventService {

    Object getEventList(int keywordId, BaseRequest baseRequest) throws ThinventBaseException;

    Object addFromUser(EventVO eventVO) throws ThinventBaseException;

    Object addFromList(EventBaseVO eventBaseVO) throws ThinventBaseException;

    Object deleteEvent(int eventId) throws ThinventBaseException;

}
