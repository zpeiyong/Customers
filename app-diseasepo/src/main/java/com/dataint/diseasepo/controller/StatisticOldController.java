package com.dataint.diseasepo.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.diseasepo.service.IStatisticService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/statistic-old")
@Slf4j
public class StatisticOldController {

    @Autowired
    private IStatisticService statisticService;

    @ApiOperation(value = "BI-获取统计信息", notes = "BI-获取BI首页基本统计信息")
    @GetMapping(value = "/getBasicInfo")
    public ResultVO getBasicInfo() {

        return ResultVO.success(statisticService.getBasicInfo());
    }

    @ApiOperation(value = "BI-获取月度统计(按时间)", notes = "BI-获取传染病月度统计(按时间)")
    @GetMapping(value = "/getMonthlyByTime")
    public ResultVO getMonthlyByTime() {

        return ResultVO.success(statisticService.getMonthlyByTime());
    }

//    @ApiOperation(value = "BI-获取月度统计(按时间)详情", notes = "BI-获取传染病月度统计(按时间)详情信息")
//    @ApiImplicitParam(paramType = "query", name = "month", value = "月份", required = true, dataType = "string")
//    @GetMapping(value = "/getMonthlyDetailByTime")
//    public ResultVO getMonthlyDetailByTime(@RequestParam String month) {
//
//        return ResultVO.success(statisticService.getMonthlyDetailByTime(month));
//    }

    @ApiOperation(value = "BI-获取月度统计(按国家)", notes = "BI-获取传染病月度统计(按国家)")
    @GetMapping(value = "/getMonthlyByCountry")
    public ResultVO getMonthlyByCountry() {

        return ResultVO.success(statisticService.getMonthlyByCountry());
    }

    @ApiOperation(value = "BI-获取疫情影响排行信息", notes = "BI-获取疫情影响排行信息")
    @GetMapping(value = "/getImpactRank")
    public ResultVO getImpactRank() {

        return ResultVO.success(statisticService.getImpactRank());
    }

    @ApiOperation(value = "BI-获取地图上需标注的重点国家信息", notes = "BI-获取地图上需标注的重点国家信息")
    @GetMapping(value = "/getMapInfo")
    public ResultVO getMapInfo() {

        return ResultVO.success(statisticService.getMapInfo());
    }

    @ApiOperation(value = "BI-获取地图上重点国家详情", notes = "BI-获取地图上重点国家详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "countryId", value = "国家ID", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "diseaseName", value = "疫情名称", required = true, dataType = "string")
    })
    @GetMapping(value = "/getMapDetail")
    public ResultVO getMapDetail(@RequestParam Integer countryId, @RequestParam String diseaseName, @ModelAttribute PageParam pageParam) {

        return ResultVO.success(statisticService.getMapDetail(countryId, diseaseName, pageParam));
    }
}
