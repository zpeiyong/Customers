package com.dataint.topic.controller;

import com.dataint.topic.model.BaseRequest;
import com.dataint.topic.model.EventBaseVO;
import com.dataint.topic.model.EventVO;
import com.dataint.topic.service.IEventService;
import com.dataint.topic.common.exception.ThinventBaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/event")
public class EventController {
    @Autowired
    private IEventService eventService;

    @GetMapping("/getEventList/{keywordId}")
    @ResponseBody
    public Object getEventList(@PathVariable("keywordId") Integer keywordId, BaseRequest baseRequest) throws ThinventBaseException {

        return eventService.getEventList(keywordId, baseRequest);
    }

    @PostMapping("/addFromUser")
    @ResponseBody
    public Object addFromUser(@RequestBody EventVO eventVO) throws ThinventBaseException {

        return eventService.addFromUser(eventVO);
    }

    @PostMapping("/addFromList")
    @ResponseBody
    public Object addFromList(@RequestBody EventBaseVO eventBaseVO) throws ThinventBaseException {

        return eventService.addFromList(eventBaseVO);
    }

    @GetMapping("/delete/{eventId}")
    @ResponseBody
    public Object deleteEvent(@PathVariable("eventId") Integer eventId) throws ThinventBaseException {

        return eventService.deleteEvent(eventId);
    }

}
