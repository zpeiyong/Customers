package com.dataint.topic.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.topic.model.vo.EventBaseVO;
import com.dataint.topic.model.vo.EventVO;
import com.dataint.topic.service.IEventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Deprecated
@RestController
@RequestMapping(value = "/event")
public class EventController {
    @Autowired
    private IEventService eventService;

    @GetMapping("/getEventList/{keywordId}")
    @ResponseBody
    public ResultVO getEventList(@PathVariable("keywordId") Integer keywordId, PageParam pageParam) {

        return ResultVO.success(eventService.getEventList(keywordId, pageParam));
    }

    @PostMapping("/addFromUser")
    @ResponseBody
    public ResultVO addFromUser(@RequestBody EventVO eventVO) {

        return ResultVO.success(eventService.addFromUser(eventVO));
    }

    @PostMapping("/addFromList")
    @ResponseBody
    public ResultVO addFromList(@RequestBody EventBaseVO eventBaseVO) {

        return ResultVO.success(eventService.addFromList(eventBaseVO));
    }

    @GetMapping("/delete/{eventId}")
    @ResponseBody
    public ResultVO deleteEvent(@PathVariable("eventId") Integer eventId) {

        return ResultVO.success( eventService.deleteEvent(eventId));
    }

}
