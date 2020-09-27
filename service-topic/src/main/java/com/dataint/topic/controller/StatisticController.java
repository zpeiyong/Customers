package com.dataint.topic.controller;

import com.dataint.cloud.common.model.ResultVO;
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
    public ResultVO getSpreadSpeed(@PathVariable("keywordId") Integer keywordId, int countDays) {

        return ResultVO.success(statisticService.getSpreadSpeed(keywordId, countDays));
    }

    @GetMapping("/getSpreadRange/{keywordId}")
    @ResponseBody
    public ResultVO getSpreadRange(@PathVariable("keywordId") Integer keywordId, int countDays) {

        return ResultVO.success(statisticService.getSpreadRange(keywordId, countDays));
    }

    @GetMapping("/periodHotPOStat")
    @ResponseBody
    public ResultVO periodHotPOStat(String beginTime, String overTime) {

        return ResultVO.success(statisticService.periodHotPOStat(beginTime, overTime));
    }

    // not use and not finished!
    @GetMapping("/sendPeriodHotPOStat")
    @ResponseBody
    public ResultVO sendPeriodHotPOStat(String beginTime, String overTime) {

        return ResultVO.success(statisticService.sendPeriodHotPOStat(beginTime, overTime));
    }

    @GetMapping("/sendPeriodDetails")
    @ResponseBody
    public ResultVO sendPeriodDetails(String beginTime, String overTime) {

        return ResultVO.success(statisticService.sendPeriodDetails(beginTime, overTime));
    }
}
