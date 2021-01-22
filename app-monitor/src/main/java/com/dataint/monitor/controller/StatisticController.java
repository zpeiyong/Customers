package com.dataint.monitor.controller;

import com.dataint.monitor.adapt.IStatisticAdapt;
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
    private IStatisticAdapt statisticAdapt;

    /**
     * 新
     */
    @ApiOperation(value = "获取[首页]基本统计数据", notes = "获取[首页]基本统计数据")
    @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long")
    @GetMapping("/getStatisticBasic")
    public Object getStatisticBasic(@RequestParam Long diseaseId) {

        return statisticAdapt.getStatisticBasic(diseaseId);
    }

    @ApiOperation(value = "获取[BI大屏]基本统计数据", notes = "获取[BI大屏]基本统计数据")
    @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long")
    @GetMapping("/getStatisticBasicBI")
    public Object getStatisticBasicBI(@RequestParam Long diseaseId) {

        return statisticAdapt.getStatisticBasicBI(diseaseId);
    }

    @ApiOperation(value = "[传播国家]获取过去7天新增国家数(折线图)", notes = "获取过去7天新增国家数(折线图)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/country/getAddTimeLine")
    public Object getCountryAddTimeLine(@RequestParam Long diseaseId,
                                          @RequestParam(required = false) String dateStr) {

        return statisticAdapt.getCountryAddTimeLine(diseaseId, dateStr);
    }

    @ApiOperation(value = "[传播国家]获取前一天国家风险排名", notes = "获取前一天国家风险排名")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/country/getRiskRank")
    public Object getCountryRiskRank(@RequestParam Long diseaseId,
                                       @RequestParam(required = false) String dateStr) {

        return statisticAdapt.getCountryRiskRank(diseaseId, dateStr);
    }

    @ApiOperation(value = "[发生事件]获取过去7天新增事件数(折线图)", notes = "获取过去7天新增事件数(折线图)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/event/getAddTimeLine")
    public Object getEventAddTimeLine(@RequestParam Long diseaseId,
                                        @RequestParam(required = false) String dateStr) {

        return statisticAdapt.getEventAddTimeLine(diseaseId, dateStr);
    }

    @ApiOperation(value = "[发生事件]获取前一天总事件数量国家排名", notes = "获取前一天总事件数量国家排名")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/event/getTotalCntRank")
    public Object getEventTotalCntRank(@RequestParam Long diseaseId,
                                         @RequestParam(required = false) String dateStr) {

        return statisticAdapt.getEventTotalCntRank(diseaseId, dateStr);
    }

    @ApiOperation(value = "[舆情数量]获取过去7天新增舆情数(折线图)", notes = "获取过去7天新增舆情数(折线图)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/article/getAddTimeLine")
    public Object getArticleAddTimeLine(@RequestParam Long diseaseId,
                                          @RequestParam(required = false) String dateStr) {

        return statisticAdapt.getArticleAddTimeLine(diseaseId, dateStr);
    }

    @ApiOperation(value = "[舆情数量]获取前一天总舆情数量国家排名", notes = "获取前一天总舆情数量国家排名")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/article/getTotalCntRank")
    public Object getArticleTotalCntRank(@RequestParam Long diseaseId,
                                           @RequestParam(required = false) String dateStr) {

        return statisticAdapt.getArticleTotalCntRank(diseaseId, dateStr);
    }

    @ApiOperation(value = "[舆情数量]获取过去7天不同来源新增舆情数(折线图)", notes = "获取过去7天不同来源新增舆情数(折线图)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/article/getAddTimeLineByType")
    public Object getArticleAddTimeLineByType(@RequestParam Long diseaseId,
                                                @RequestParam(required = false) String dateStr) {

        return statisticAdapt.getArticleAddTimeLineByType(diseaseId, dateStr);
    }

    @ApiOperation(value = "[确诊趋势]获取过去7天新增确诊数(折线图)", notes = "获取过去7天新增确诊数(折线图)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/confirmed/getAddTimeLine")
    public Object getConfirmedTimeLine(@RequestParam Long diseaseId,
                                         @RequestParam(required = false) String dateStr) {

        return statisticAdapt.getConfirmedTimeLine(diseaseId, dateStr);
    }

    @ApiOperation(value = "[确诊趋势]获取前一天确诊数量排名", notes = "获取前一天确诊数量排名")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/confirmed/getTotalCntRank")
    public Object getConfirmedTotalCntRank(@RequestParam Long diseaseId,
                                             @RequestParam(required = false) String dateStr) {

        return statisticAdapt.getConfirmedTotalCntRank(diseaseId, dateStr);
    }

    @ApiOperation(value = "[治愈趋势]获取过去7天新增治愈数(折线图)", notes = "获取过去7天新增治愈数(折线图)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/cured/getAddTimeLine")
    public Object getCuredTimeLine(@RequestParam Long diseaseId,
                                     @RequestParam(required = false) String dateStr) {

        return statisticAdapt.getCuredTimeLine(diseaseId, dateStr);
    }

    @ApiOperation(value = "[治愈率]获取前一天治愈百分比排名", notes = "获取前一天治愈百分比排名")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/cured/getTotalCntRank")
    public Object getCuredTotalCntRank(@RequestParam Long diseaseId,
                                         @RequestParam(required = false) String dateStr) {

        return statisticAdapt.getCuredTotalCntRank(diseaseId, dateStr);
    }

    @ApiOperation(value = "[死亡趋势]获取过去7天新增死亡数(折线图)", notes = "获取过去7天新增死亡数(折线图)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/death/getAddTimeLine")
    public Object getDeathTimeLine(@RequestParam Long diseaseId,
                                     @RequestParam(required = false) String dateStr) {

        return statisticAdapt.getDeathTimeLine(diseaseId, dateStr);
    }

    @ApiOperation(value = "[死亡趋势]获取前一天死亡百分比排名", notes = "获取前一天死亡百分比排名")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/death/getTotalCntRank")
    public Object getDeathTotalCntRank(@RequestParam Long diseaseId,
                                         @RequestParam(required = false) String dateStr) {

        return statisticAdapt.getDeathTotalCntRank(diseaseId, dateStr);
    }


    @ApiOperation(value = "获取需要在地图上标记的国家列表", notes = "获取需要在地图上标记的国家列表")
    @GetMapping("/getMapCountryList")
    public Object getMapCountryList() {

        return statisticAdapt.getMapCountryList();
    }

    @ApiOperation(value = "[地图]获取全球传染病风险指数时间轴", notes = "[地图]获取全球传染病风险指数时间轴")
    @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    @GetMapping("/getGlobalRiskTimeLine")
    public Object getGlobalRiskTimeLine(@RequestParam(required = false) String dateStr) {

        return statisticAdapt.getGlobalRiskTimeLine(dateStr);
    }

}
