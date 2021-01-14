package com.dataint.monitor.controller;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.monitor.adapt.IArticleAdapt;
import com.dataint.monitor.model.form.ArticleUpdateForm;
import com.dataint.monitor.model.form.StoreDataForm;
import com.dataint.monitor.model.param.ArticleListQueryParam;
import com.dataint.monitor.service.IArticleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/article")
@Slf4j
public class ArticleController {

    @Autowired
    private IArticleAdapt articleAdapt;

    @Autowired
    private IArticleService articleService;

    @ApiOperation(value = "保存舆情", notes = "保存一条舆情")
    @ApiImplicitParam(name = "storeDataForm", value = "保存舆情form表单", required = true, dataType = "StoreDataForm")
    @PostMapping("/store")
    public Object store(@Valid @RequestBody StoreDataForm storeDataForm) {
        log.debug("Name: {}", storeDataForm);
        return  articleAdapt.storeData(storeDataForm);
    }

    @RequestMapping(value = "/queryEventList",method = RequestMethod.GET)
    @ApiOperation(value = "BI大屏事件信息查询",notes = "BI大屏事件查询")
    @ResponseBody
    public  Object  queryEventList(Long diseaseId,Long pageSize, Long current,String releaseTime){
        JSONObject eventList = articleService.queryEventList(diseaseId, pageSize, current,releaseTime);
        return  eventList;
    }

    /**
     * BI展示模块
     */
    @ApiOperation(value = "获取BI舆情列表", notes = "获取BI舆情信息列表")
    @GetMapping("/queryBasicList")
    public Object queryBasicList(@ModelAttribute PageParam pageParam) {

        return articleAdapt.queryBasicList(pageParam);
    }

    @ApiOperation(value = "根据id获取ArticleBasic", notes = "根据id获取ArticleBasic")
    @ApiImplicitParam(paramType = "path", name = "id", value = "舆情ID", required = true, dataType = "long")
    @GetMapping("/basic/{id}")
    public Object queryBasicById(@PathVariable Long id) {

        return articleAdapt.queryBasicById(id);
    }

    @ApiOperation(value = "获取地图上国家疫情统计下的舆情列表", notes = "获取地图上国家疫情统计下的舆情列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "countryId", value = "国家ID", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "diseaseName", value = "疫情名称", required = true, dataType = "string")
    })
    @GetMapping("/queryMapBasicList")
    public Object queryMapBasicList(@RequestParam Long countryId, @RequestParam String diseaseName, @ModelAttribute PageParam pageParam) {

        return articleAdapt.queryMapBasicList(countryId, diseaseName, pageParam);
    }


    /**
     * Web疫情讯息模块
     */
    @ApiOperation(value = "获取舆情列表", notes = "获取舆情信息列表")
    @GetMapping("/normal/getArticleList")
    public Object getArticleList(@ModelAttribute ArticleListQueryParam articleListQueryParam) {

        return articleAdapt.getArticleList(articleListQueryParam);
    }

    @ApiOperation(value = "根据舆情id获取舆情信息", notes = "根据舆情id获取舆情信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "舆情ID", required = true, dataType = "long")
    @GetMapping(value = "/normal/{id}")
    public Object getArticleById(@PathVariable Long id) {
        log.debug("get with id: {}", id);

        return articleAdapt.getArticleById(id);

    }

    @ApiOperation(value = "单个/批量删除舆情", notes = "根据舆情id列表删除舆情信息")
    @ApiImplicitParam(paramType = "query", name = "idListStr", value = "舆情IDs", required = true, dataType = "string")
    @DeleteMapping(value = "/normal/delArticles")
    public Object delArticles(@RequestParam String idListStr) {
        log.debug("delete with id: {}", idListStr);

        return articleAdapt.delArticles(idListStr);
    }

    @ApiOperation(value = "增加关键词", notes = "增加关键词(单条/批量)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "idListStr", value = "舆情IDs", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "keyword", value = "关键词", required = true, dataType = "string")
    })
    @PutMapping(value = "/addKeyword")
    public Object addKeyword(@RequestParam String idListStr, @RequestParam String keyword) {
        log.debug("add keyword with ids: {}", idListStr);
        return articleAdapt.addKeyword(idListStr, keyword);
    }

    @ApiOperation(value = "删除关键词", notes = "删除关键词(单条)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "舆情ID", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "keyword", value = "关键词", required = true, dataType = "string")
    })
    @PutMapping(value = "/delKeyword/{id}")
    public Object delKeyword(@PathVariable Long id, @RequestParam String keyword) {
        log.debug("delete keyword with id: {}", id);

        return articleAdapt.delKeyword(id, keyword);
    }

    @ApiOperation(value = "更新舆情等级", notes = "根据舆情id更新舆情等级")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "舆情ID", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "levelId", value = "舆情等级ID", required = true, dataType = "long")
    })
    @PutMapping(value = "/updateLevel/{id}")
    public Object updateLevel(@PathVariable Long id, @RequestParam Long levelId) {
        log.debug("update level with id: {}", id);

        return articleAdapt.updateLevel(id, levelId);
    }

    @ApiOperation(value = "更新舆情详情信息", notes = "根据舆情id更新舆情详情信息")
    @ApiImplicitParam(paramType = "query", name = "articleUpdateForm", value = "舆情详情信息", required = true, dataType = "ArticleUpdateForm")
    @PutMapping(value = "/updateArticle")
    public Object updateArticle(@RequestBody ArticleUpdateForm articleUpdateForm) {
        return articleAdapt.updateArticle(articleUpdateForm);
    }



    /**
     * 统计/分析模块
     */
    @ApiOperation(value = "获取gmtRelease列表", notes = "根据关键词查询gmtRelease列表")
    @ApiImplicitParam(paramType = "query", name = "keyword", value = "关键词", required = true, dataType = "string")
    @GetMapping("/searchByKeyword")
    public Object searchByKeyword(@RequestParam String keyword) {

        return articleAdapt.searchByKeyword(keyword);
    }

    @ApiOperation(value = "获取报告所需舆情数据", notes = "根据起始时间段, 获取报告所需舆情数据内容")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "舆情起始时间", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "舆情结束时间", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "舆情报告类型", required = true, dataType = "string")
    })
    @GetMapping("/queryReport")
    public Object queryDailyReport(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime, @RequestParam(value = "type") String type) {

        return articleAdapt.queryReportContent(startTime, endTime, type);
    }

}
