package com.dataint.topic.controller;

import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.topic.model.EventBaseVO;
import com.dataint.topic.model.EventVO;
import com.dataint.topic.service.IEventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/event")
public class EventController {
    @Autowired
    private IEventService eventService;

    @GetMapping("/getEventList/{keywordId}")
    @ResponseBody
    public Object getEventList(@PathVariable("keywordId") Integer keywordId, PageParam pageParam) {

        return eventService.getEventList(keywordId, pageParam);
    }

    @PostMapping("/addFromUser")
    @ResponseBody
    public Object addFromUser(@RequestBody EventVO eventVO) {

        return eventService.addFromUser(eventVO);
    }

    @PostMapping("/addFromList")
    @ResponseBody
    public Object addFromList(@RequestBody EventBaseVO eventBaseVO) {

        return eventService.addFromList(eventBaseVO);
    }

    @GetMapping("/delete/{eventId}")
    @ResponseBody
    public Object deleteEvent(@PathVariable("eventId") Integer eventId) {

        return eventService.deleteEvent(eventId);
    }

}
