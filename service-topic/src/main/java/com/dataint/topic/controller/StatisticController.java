package com.dataint.topic.controller;

import com.dataint.topic.service.IStatisticService;
import com.dataint.topic.common.exception.ThinventBaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/statistic")
public class StatisticController {
    @Autowired
    private IStatisticService statisticService;

    @GetMapping("/getSpreadSpeed/{keywordId}")
    @ResponseBody
    public Object getSpreadSpeed(@PathVariable("keywordId") Integer keywordId, int countDays) throws ThinventBaseException {

        return statisticService.getSpreadSpeed(keywordId, countDays);
    }

    @GetMapping("/getSpreadRange/{keywordId}")
    @ResponseBody
    public Object getSpreadRange(@PathVariable("keywordId") Integer keywordId, int countDays) throws ThinventBaseException {

        return statisticService.getSpreadRange(keywordId, countDays);
    }

    @GetMapping("/periodHotPOStat")
    @ResponseBody
    public Object periodHotPOStat(String beginTime, String overTime) throws ThinventBaseException {

        return statisticService.periodHotPOStat(beginTime, overTime);
    }

    // not use and not finished!
    @GetMapping("/sendPeriodHotPOStat")
    @ResponseBody
    public Object sendPeriodHotPOStat(String beginTime, String overTime) throws ThinventBaseException {

        return statisticService.sendPeriodHotPOStat(beginTime, overTime);
    }

    @GetMapping("/sendPeriodDetails")
    @ResponseBody
    public Object sendPeriodDetails(String beginTime, String overTime) throws ThinventBaseException {

        return statisticService.sendPeriodDetails(beginTime, overTime);
    }
}
