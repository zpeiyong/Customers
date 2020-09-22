package com.dataint.topic.controller;

import com.dataint.topic.service.IStatisticService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/statistic")
public class StatisticController {
    @Autowired
    private IStatisticService statisticService;

    @GetMapping("/getSpreadSpeed/{keywordId}")
    @ResponseBody
    public Object getSpreadSpeed(@PathVariable("keywordId") Integer keywordId, int countDays) {

        return statisticService.getSpreadSpeed(keywordId, countDays);
    }

    @GetMapping("/getSpreadRange/{keywordId}")
    @ResponseBody
    public Object getSpreadRange(@PathVariable("keywordId") Integer keywordId, int countDays) {

        return statisticService.getSpreadRange(keywordId, countDays);
    }

    @GetMapping("/periodHotPOStat")
    @ResponseBody
    public Object periodHotPOStat(String beginTime, String overTime) {

        return statisticService.periodHotPOStat(beginTime, overTime);
    }

    // not use and not finished!
    @GetMapping("/sendPeriodHotPOStat")
    @ResponseBody
    public Object sendPeriodHotPOStat(String beginTime, String overTime) {

        return statisticService.sendPeriodHotPOStat(beginTime, overTime);
    }

    @GetMapping("/sendPeriodDetails")
    @ResponseBody
    public Object sendPeriodDetails(String beginTime, String overTime) {

        return statisticService.sendPeriodDetails(beginTime, overTime);
    }
}
