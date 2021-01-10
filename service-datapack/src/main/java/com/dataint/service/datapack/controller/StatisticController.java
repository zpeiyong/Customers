package com.dataint.service.datapack.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.service.datapack.service.IStatisticService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/statistic")
@Slf4j
@CrossOrigin
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
                                        @RequestParam Long countryId) {

        return ResultVO.success(statisticService.getMapInfoByCountry(startTime, endTime, countryId));
    }


    /**
     * 新
     */
    @ApiOperation(value = "获取[首页]基本统计数据", notes = "获取[首页]基本统计数据")
    @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long")
    @GetMapping("/getStatisticBasic")
    public ResultVO getStatisticBasic(@RequestParam Long diseaseId) {

        return ResultVO.success(statisticService.getStatisticBasic(diseaseId));
    }

    @ApiOperation(value = "获取[BI大屏]基本统计数据", notes = "获取[BI大屏]基本统计数据")
    @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long")
    @GetMapping("/getStatisticBasicBI")
    public ResultVO getStatisticBasicBI(@RequestParam Long diseaseId) {

        return ResultVO.success(statisticService.getStatisticBasicBI(diseaseId));
    }

    @ApiOperation(value = "[传播国家]获取过去7天新增国家数(折线图)", notes = "获取过去7天新增国家数(折线图)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/country/getAddTimeLine")
    public ResultVO getCountryAddTimeLine(@RequestParam Long diseaseId,
                                          @RequestParam(required = false) String dateStr) {

        return ResultVO.success(statisticService.getCountryAddTimeLine(diseaseId, dateStr, 7));
    }

    @ApiOperation(value = "[传播国家]获取前一天国家风险排名", notes = "获取前一天国家风险排名")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/country/getRiskRank")
    public ResultVO getCountryRiskRank(@RequestParam Long diseaseId,
                                       @RequestParam(required = false) String dateStr) {

        return ResultVO.success(statisticService.getCountryRiskRank(diseaseId, dateStr));
    }

    @ApiOperation(value = "[发生事件]获取过去7天新增事件数(折线图)", notes = "获取过去7天新增事件数(折线图)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/event/getAddTimeLine")
    public ResultVO getEventAddTimeLine(@RequestParam Long diseaseId,
                                        @RequestParam(required = false) String dateStr) {

        return ResultVO.success(statisticService.getEventAddTimeLine(diseaseId, dateStr, 7));
    }

    @ApiOperation(value = "[发生事件]获取前一天总事件数量国家排名", notes = "获取前一天总事件数量国家排名")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/event/getTotalCntRank")
    public ResultVO getEventTotalCntRank(@RequestParam Long diseaseId,
                                         @RequestParam(required = false) String dateStr) {

        return ResultVO.success(statisticService.getEventTotalCntRank(diseaseId, dateStr));
    }

    @ApiOperation(value = "[舆情数量]获取过去7天新增舆情数(折线图)", notes = "获取过去7天新增舆情数(折线图)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/article/getAddTimeLine")
    public ResultVO getArticleAddTimeLine(@RequestParam Long diseaseId,
                                          @RequestParam(required = false) String dateStr) {

        return ResultVO.success(statisticService.getArticleAddTimeLine(diseaseId, dateStr, 7));
    }

    @ApiOperation(value = "[舆情数量]获取前一天总舆情数量国家排名", notes = "获取前一天总舆情数量国家排名")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/article/getTotalCntRank")
    public ResultVO getArticleTotalCntRank(@RequestParam Long diseaseId,
                                           @RequestParam(required = false) String dateStr) {

        return ResultVO.success(statisticService.getArticleTotalCntRank(diseaseId, dateStr));
    }

    @ApiOperation(value = "[舆情数量]获取过去7天不同来源新增舆情数(折线图)", notes = "获取过去7天不同来源新增舆情数(折线图)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/article/getArticleAddTimeLineByType")
    public ResultVO getArticleAddTimeLineByType(@RequestParam Long diseaseId,
                                          @RequestParam(required = false) String dateStr) {

        return ResultVO.success(statisticService.getArticleAddTimeLineByType(diseaseId, dateStr, 7));
    }

    @ApiOperation(value = "[确诊趋势]获取过去7天新增确诊数(折线图)", notes = "获取过去7天新增确诊数(折线图)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/confirmed/getAddTimeLine")
    public ResultVO getConfirmedTimeLine(@RequestParam Long diseaseId,
                                          @RequestParam(required = false) String dateStr) {

        return ResultVO.success(statisticService.getConfirmedTimeLine(diseaseId, dateStr, 7));
    }

    @ApiOperation(value = "[确诊趋势]获取前一天确诊数量排名", notes = "获取前一天确诊数量排名")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/confirmed/getTotalCntRank")
    public ResultVO getConfirmedTotalCntRank(@RequestParam Long diseaseId,
                                           @RequestParam(required = false) String dateStr) {

        return ResultVO.success(statisticService.getConfirmedTotalCntRank(diseaseId, dateStr));
    }


    @ApiOperation(value = "[治愈趋势]获取过去7天新增治愈数(折线图)", notes = "获取过去7天新增治愈数(折线图)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/cured/getAddTimeLine")
    public ResultVO getCuredTimeLine(@RequestParam Long diseaseId,
                                         @RequestParam(required = false) String dateStr) {

        return ResultVO.success(statisticService.getCuredTimeLine(diseaseId, dateStr, 7));
    }

    @ApiOperation(value = "[治愈率]获取前一天治愈百分比排名", notes = "获取前一天治愈百分比排名")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/cured/getTotalCntRank")
    public ResultVO getCuredTotalCntRank(@RequestParam Long diseaseId,
                                             @RequestParam(required = false) String dateStr) {

        return ResultVO.success(statisticService.getCuredTotalCntRank(diseaseId, dateStr));
    }



    @ApiOperation(value = "[死亡趋势]获取过去7天新增死亡数(折线图)", notes = "获取过去7天新增死亡数(折线图)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/death/getAddTimeLine")
    public ResultVO getDeathTimeLine(@RequestParam Long diseaseId,
                                         @RequestParam(required = false) String dateStr) {

        return ResultVO.success(statisticService.getDeathTimeLine(diseaseId, dateStr, 7));
    }

    @ApiOperation(value = "[死亡趋势]获取前一天死亡百分比排名", notes = "获取前一天死亡百分比排名")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/death/getTotalCntRank")
    public ResultVO getDeathTotalCntRank(@RequestParam Long diseaseId,
                                             @RequestParam(required = false) String dateStr) {

        return ResultVO.success(statisticService.getDeathTotalCntRank(diseaseId, dateStr));
    }



    @ApiOperation(value = "获取需要在地图上标记的国家列表", notes = "获取需要在地图上标记的国家列表")
    @GetMapping("/getMapCountryList")
    public ResultVO getMapCountryList() {

        return ResultVO.success(statisticService.getMapCountryList());
    }

    @ApiOperation(value = "[地图]获取全球传染病风险指数时间轴", notes = "[地图]获取全球传染病风险指数时间轴")
    @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    @GetMapping("/getGlobalRiskTimeLine")
    public ResultVO getGlobalRiskTimeLine(@RequestParam(required = false) String dateStr) {

        return ResultVO.success(statisticService.getGlobalRiskTimeLine(dateStr, 14));
    }

}
