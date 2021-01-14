package com.dataint.service.datapack.controller;

import com.dataint.cloud.common.model.Constants;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.service.datapack.model.form.ArticleUpdateForm;
import com.dataint.service.datapack.model.form.StoreDataForm;
import com.dataint.service.datapack.model.param.ArticleListQueryParam;
import com.dataint.service.datapack.service.IArticleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/article")
@Slf4j
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @ApiOperation(value = "保存舆情", notes = "保存一条舆情")
    @ApiImplicitParam(name = "storeDataForm", value = "保存舆情form表单", required = true, dataType = "StoreDataForm")
    @PostMapping("/store")
    public ResultVO store(@Valid @RequestBody StoreDataForm storeDataForm) {
        log.debug("Name: {}", storeDataForm);

        articleService.storeData(storeDataForm);
        return ResultVO.success("存储成功!");
    }

    /**
     * BI展示模块
     */
    @RequestMapping(value = "/queryEventList",method = RequestMethod.GET)
    @ApiOperation(value = "BI大屏事件信息查询",notes = "BI大屏事件查询")
    @ResponseBody
    public ResultVO queryArticleList(Long diseaseId,int pageSize, int current,String  releaseTime) {

        return ResultVO.success(articleService.queryEventList(diseaseId,pageSize,current,releaseTime));
    }

    @ApiOperation(value = "获取BI舆情列表", notes = "获取BI舆情信息列表")
    @GetMapping("/queryBasicList")
    public ResultVO queryBasicList(@ModelAttribute PageParam pageParam) {

        return ResultVO.success(articleService.queryBasicList(pageParam));
    }

    @ApiOperation(value = "根据id获取ArticleBasic", notes = "根据id获取ArticleBasic")
    @ApiImplicitParam(paramType = "path", name = "id", value = "舆情ID", required = true, dataType = "long")
    @GetMapping("/basic/{id}")
    public ResultVO queryBasicById(@PathVariable Long id) {

        return ResultVO.success(articleService.queryBasicById(id));
    }

    @ApiOperation(value = "获取地图上国家疫情统计下的舆情列表", notes = "获取地图上国家疫情统计下的舆情列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "countryId", value = "国家ID", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "diseaseName", value = "疫情名称", required = true, dataType = "string")
    })
    @GetMapping("/queryMapBasicList")
    public ResultVO queryMapBasicList(@RequestParam Long countryId, @RequestParam String diseaseName, @ModelAttribute PageParam pageParam) {

        return ResultVO.success(articleService.queryMapBasicList(countryId, diseaseName, pageParam));
    }


    /**
     * Web视点模块
     */
    @ApiOperation(value = "获取舆情列表", notes = "获取舆情信息列表")
    @GetMapping("/normal/getArticleList")
    public ResultVO getArticleList(@ModelAttribute ArticleListQueryParam articleListQueryParam) {

        return articleService.getArticleList(articleListQueryParam);
    }

    @ApiOperation(value = "根据舆情id获取舆情信息", notes = "根据舆情id获取舆情信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "舆情ID", required = true, dataType = "long")
    @GetMapping(value = "/normal/{id}")
    public ResultVO getArticleById(@PathVariable Long id) {
        log.debug("get with id: {}", id);

        return ResultVO.success(articleService.getArticleById(id));
    }

    @ApiOperation(value = "获取舆情信息相似文章", notes = "根据id获取舆情信息相似文章")
    @ApiImplicitParam(paramType = "path", name = "id", value = "舆情ID", required = true, dataType = "long")
    @GetMapping(value = "/normal/similar/{id}")
    public ResultVO getSimilarArticlesById(@PathVariable Long id, @ModelAttribute PageParam pageParam) {
        log.debug("get similar articles by id: {}", id);

        return ResultVO.success(articleService.getSimilarArticlesById(id, pageParam));
    }

    @ApiOperation(value = "单个/批量删除舆情", notes = "根据舆情id列表删除舆情信息")
    @ApiImplicitParam(paramType = "query", name = "idListStr", value = "舆情IDs", required = true, dataType = "string")
    @DeleteMapping(value = "/normal/delArticles")
    public ResultVO delArticles(@RequestParam String idListStr) {
        log.debug("delete with id: {}", idListStr);

        List<Long> idList = Arrays.stream(idListStr.split(Constants.SPLITTER)).map(Long::valueOf).collect(Collectors.toList());

        // 循环删除id列表对应数据
        for (Long articleId : idList) {
            articleService.delArticleById(articleId);
        }

        return ResultVO.success(true);
    }

    @ApiOperation(value = "增加关键词", notes = "增加关键词(单条/批量)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "idListStr", value = "舆情IDs", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "keyword", value = "关键词", required = true, dataType = "string")
    })
    @PutMapping(value = "/addKeyword")
    public ResultVO addKeyword(@RequestParam String idListStr, @RequestParam String keyword) {
        log.debug("add keyword with ids: {}", idListStr);

        List<Long> idList = Arrays.stream(idListStr.split(Constants.SPLITTER)).map(Long::valueOf).collect(Collectors.toList());

        return ResultVO.success(articleService.addKeyword(idList, keyword));
    }

    @ApiOperation(value = "删除关键词", notes = "删除关键词(单条)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "舆情ID", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "keyword", value = "关键词", required = true, dataType = "string")
    })
    @PutMapping(value = "/delKeyword/{id}")
    public ResultVO delKeyword(@PathVariable Long id, @RequestParam String keyword) {
        log.debug("delete keyword with id: {}", id);

        return ResultVO.success(articleService.delKeyword(id, keyword));
    }

    @ApiOperation(value = "更新舆情等级", notes = "根据舆情id更新舆情等级")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "舆情ID", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "levelId", value = "舆情等级ID", required = true, dataType = "long")
    })
    @PutMapping(value = "/updateLevel/{id}")
    public ResultVO updateLevel(@PathVariable Long id, @RequestParam Long levelId) {
        log.debug("update level with id: {}", id);

        return ResultVO.success(articleService.updateLevel(id, levelId));
    }

    @ApiOperation(value = "更新舆情详情信息", notes = "根据舆情id更新舆情详情信息")
    @ApiImplicitParam(paramType = "query", name = "articleUpdateForm", value = "舆情详情信息", required = true, dataType = "ArticleUpdateForm")
    @PutMapping(value = "/updateArticle")
    public ResultVO updateArticle(@RequestBody ArticleUpdateForm articleUpdateForm) {
        return ResultVO.success(articleService.updateArticle(articleUpdateForm));
    }



    /**
     * 统计/分析模块
     */
    @ApiOperation(value = "获取gmtRelease列表", notes = "根据关键词查询gmtRelease列表")
    @ApiImplicitParam(paramType = "query", name = "keyword", value = "关键词", required = true, dataType = "string")
    @GetMapping("/searchByKeyword")
    public ResultVO searchByKeyword(@RequestParam String keyword) {

        return ResultVO.success(articleService.searchByKeyword(keyword));
    }

    @ApiOperation(value = "获取报告需要的舆情数据", notes = "根据舆情id列表获取报告需要的舆情数据")
    @ApiImplicitParam(paramType = "query", name = "idListStr", value = "舆情数据id列表", allowMultiple = true, required = true, dataType = "string")
    @GetMapping("/queryArticlesByIdList")
    public ResultVO queryArticlesByIdList(@RequestParam List<Long> idListStr) {
        log.debug("query articles by articleId List: {}", idListStr);

        return ResultVO.success(articleService.queryReportContent(idListStr));
    }

}
