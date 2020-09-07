package com.dataint.service.datapack.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.service.datapack.service.IStatisticService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/statistic")
@Slf4j
public class StatisticController {

    @Autowired
    private IStatisticService statisticService;

    @ApiOperation(value = "获取指定日期舆情相关统计数据", notes = "获取指定日期舆情相关统计数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "统计起始时间", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "统计截止时间", required = true, dataType = "String")
    })
    @GetMapping("/getPeriodBasic")
    public ResultVO getPeriodBasic(@RequestParam String startTime, @RequestParam String endTime) {

        return ResultVO.success(statisticService.getPeriodBasic(startTime, endTime));
    }

    @ApiOperation(value = "获取舆情总数", notes = "获取舆情总数")
    @GetMapping("/getArticleTotal")
    public ResultVO getArticleTotal() {

        return ResultVO.success(statisticService.getArticleTotal());
    }

    @ApiOperation(value = "计算获取指定时间内舆情数量", notes = "根据国家id和编号, 计算获取指定时间内舆情数量")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "统计起始时间", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "统计截止时间", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "countryId", value = "国家id", required = true, dataType = "String")
    })
    @GetMapping("/getMapInfoByCountry")
    public ResultVO getMapInfoByCountry(@RequestParam String startTime,
                                        @RequestParam String endTime,
                                        @RequestParam Integer countryId) {

        return ResultVO.success(statisticService.getMapInfoByCountry(startTime, endTime, countryId));
    }
}
