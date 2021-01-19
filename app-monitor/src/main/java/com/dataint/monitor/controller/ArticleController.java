package com.dataint.monitor.controller;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.Constants;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.cloud.common.utils.JWTUtil;
import com.dataint.monitor.adapt.IArticleAdapt;
import com.dataint.monitor.model.form.ArticleUpdateForm;
import com.dataint.monitor.model.form.ArticleKeyWordsForm;
import com.dataint.monitor.model.param.ArticleListQueryParam;
import com.dataint.monitor.service.IArticleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/article")
@Slf4j
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IArticleAdapt articleAdapt;

    /**
     * 全景(BI)展示模块
     */
    @ApiOperation(value = "BI大屏事件信息查询", notes = "BI大屏事件查询")
    @RequestMapping(value = "/queryEventList", method = RequestMethod.GET)
    public Object queryEventList(Long diseaseId, Long pageSize, Long current, String releaseTime, String searchTime) {
        JSONObject eventList = articleService.queryEventList(diseaseId, pageSize, current, releaseTime, searchTime);
        return eventList;
    }

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
            @ApiImplicitParam(paramType = "query", name = "countryId", value = "国家ID", required = false, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "疫情ID", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "searchTime", value = "查询时间", required = false, dataType = "string")
    })
    @GetMapping("/queryMapBasicList")
    public Object queryMapBasicList(@RequestParam(required = false) Long countryId,
                                    @RequestParam Long diseaseId,
                                    @RequestParam(required = false) String searchTime,
                                    @ModelAttribute PageParam pageParam) {

        return articleAdapt.queryMapBasicList(countryId, diseaseId, searchTime, pageParam);
    }


    /**
     * Web视点模块
     */
    @ApiOperation(value = "获取舆情列表", notes = "获取舆情信息列表")
    @GetMapping(value = "/normal/getArticleList")
    public ResultVO getArticleList(@ModelAttribute ArticleListQueryParam articleListQueryParam,
                                   @RequestHeader(Constants.AUTHORIZE_ACCESS_TOKEN) String accessToken) {
        log.debug("get article: {}", articleListQueryParam);

        // 解析token获取userId
        Long userId = JWTUtil.getUserId(accessToken);
        String systemType = JWTUtil.getSystemType(accessToken);

        return articleService.getArticleList(articleListQueryParam, userId, systemType);
    }

    @ApiOperation(value = "获取舆情信息", notes = "获取舆情信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "舆情ID", required = true, dataType = "long")
    @GetMapping(value = "/normal/{id}")
    public ResultVO getArticleById(@PathVariable Long id,
                                   @RequestHeader(Constants.AUTHORIZE_ACCESS_TOKEN) String accessToken) {
        log.debug("get with id: {}", id);

        // 解析token获取userId
        Long userId = JWTUtil.getUserId(accessToken);
        String systemType = JWTUtil.getSystemType(accessToken);

        return articleService.getArticleById(userId, id, systemType);
    }

    @ApiOperation(value = "获取舆情信息相似文章", notes = "根据id获取舆情信息相似文章")
    @ApiImplicitParam(paramType = "path", name = "id", value = "舆情ID", required = true, dataType = "long")
    @GetMapping(value = "/normal/similar/{id}")
    public ResultVO getSimilarArticlesById(@PathVariable Long id,
                                           @ModelAttribute PageParam pageParam,
                                           @RequestHeader(Constants.AUTHORIZE_ACCESS_TOKEN) String accessToken) {
        log.debug("get with id: {}", id);

        // 解析token获取userId
        Long userId = JWTUtil.getUserId(accessToken);
        String systemType = JWTUtil.getSystemType(accessToken);

        return articleService.getSimilarArticlesById(userId, id, pageParam, systemType);
    }


    @ApiOperation(value = "单个/批量删除舆情", notes = "根据舆情id列表删除舆情信息")
    @ApiImplicitParam(paramType = "query", name = "idListStr", value = "舆情IDs", required = true, dataType = "string")
    @PutMapping(value = "/normal/delArticles")
    public Object delArticles(@RequestBody ArticleKeyWordsForm articleKeyWordsForm) {
        String idListStr = articleKeyWordsForm.getIdListStr();
        log.debug("delete with id: {}", idListStr);

        return articleService.delArticles(idListStr);
    }

    @ApiOperation(value = "增加关键词", notes = "增加关键词(单条/批量)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "idListStr", value = "舆情IDs", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "keyword", value = "关键词", required = true, dataType = "string")
    })
    @PutMapping(value = "/addKeyword")
    public Object addKeyword(@RequestBody ArticleKeyWordsForm articleKeyWordsForm, @RequestHeader(Constants.AUTHORIZE_ACCESS_TOKEN) String accessToken) {
        String keyword = articleKeyWordsForm.getKeyword();
        String idListStr = articleKeyWordsForm.getIdListStr();
        log.debug("add keyword with ids: {}", idListStr);
        Long userId = JWTUtil.getUserId(accessToken);

        return articleService.addKeyword(userId, idListStr, keyword);
    }

    @ApiOperation(value = "删除关键词", notes = "删除关键词(单条)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "舆情ID", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "keyword", value = "关键词", required = true, dataType = "string")
    })
    @PutMapping(value = "/delKeyword/{id}")
    public Object delKeyword(@PathVariable Long id, @RequestBody ArticleKeyWordsForm articleKeyWordsParam, @RequestHeader(Constants.AUTHORIZE_ACCESS_TOKEN) String accessToken) {
        log.debug("delete keyword with id: {}", id);
        String keyword = articleKeyWordsParam.getKeyword();
        Long userId = JWTUtil.getUserId(accessToken);

        return articleService.delKeyword(userId, id, keyword);
    }

    @ApiOperation(value = "更新舆情详情信息", notes = "根据舆情id更新舆情详情信息")
    @ApiImplicitParam(paramType = "query", name = "articleUpdateForm", value = "舆情详情信息", required = true, dataType = "ArticleUpdateForm")
    @PutMapping(value = "/updateArticle")
    public Object updateArticle(@RequestBody ArticleUpdateForm articleUpdateForm, @RequestHeader(Constants.AUTHORIZE_ACCESS_TOKEN) String accessToken) {
        Long userId = JWTUtil.getUserId(accessToken);

        return articleService.updateArticle(userId, articleUpdateForm);
    }
}
