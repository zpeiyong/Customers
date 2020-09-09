package com.dataint.monitor.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.cloud.common.utils.JWTUtil;
import com.dataint.monitor.model.form.ArticleUpdateForm;
import com.dataint.monitor.model.param.ArticleListQueryParam;
import com.dataint.monitor.provider.SysAdminProvider;
import com.dataint.monitor.service.IArticleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/article")
@Slf4j
public class ArticleController {

    @Autowired
    private IArticleService articleService;


    @Autowired
    private SysAdminProvider sysAdminProvider;

    /* BI */
    @ApiOperation(value = "BI-获取最新舆情列表", notes = "BI-获取BI首页最新舆情列表")
    @GetMapping(value = "/getLatestList")
    public ResultVO getLatestList(@ModelAttribute PageParam pageParam) {

        return articleService.getLatestList(pageParam);
    }

    @ApiOperation(value = "根据id获取ArticleBasic", notes = "根据id获取ArticleBasic")
    @ApiImplicitParam(paramType = "path", name = "id", value = "舆情ID", required = true, dataType = "long")
    @GetMapping("/basic/{id}")
    public ResultVO queryBasicById(@PathVariable Integer id) {

        return articleService.getArticleBasicById(id);
    }


    /* Web */
    @ApiOperation(value = "获取舆情列表", notes = "获取舆情信息列表")
    @GetMapping(value = "/normal/getArticleList")
    public ResultVO getArticleList(@ModelAttribute ArticleListQueryParam articleListQueryParam, @CookieValue("access_token") String accessToken) {
        log.debug("get article: {}", articleListQueryParam);


        // 解析token获取userId
        //int userId = parseUserIdFromJWT(accessToken);
        Integer userId = JWTUtil.getUserId(accessToken);

        return ResultVO.success(articleService.getArticleList(userId, articleListQueryParam));
    }

    @ApiOperation(value = "获取舆情信息", notes = "获取舆情信息")
    @ApiImplicitParam(paramType = "path", name = "id", value = "舆情ID", required = true, dataType = "long")
    @GetMapping(value = "/normal/{id}")
    public ResultVO getArticleById(@PathVariable Integer id, @CookieValue("access_token") String accessToken) {
        log.debug("get with id: {}", id);

        // 解析token获取userId
        Integer userId = JWTUtil.getUserId(accessToken);

        return ResultVO.success(articleService.getArticleById(userId, id));
    }

    @ApiOperation(value = "单个/批量删除舆情", notes = "根据舆情id列表删除舆情信息")
    @ApiImplicitParam(paramType = "query", name = "idList", value = "舆情ID列表", required = true, allowMultiple = true, dataType = "long")
    @DeleteMapping(value = "/normal/delArticles/{idList}")
    public ResultVO delArticles(@PathVariable List<Integer> idList) {
        log.debug("delete with id: {}", idList);
        return articleService.delArticles(idList);
    }

    @ApiOperation(value = "更新用户舆情信息收藏", notes = "更新用户舆情信息收藏")
    @ApiImplicitParam(paramType = "query", name = "idList", value = "舆情ID", required = true, allowMultiple = true, dataType = "long")
    @PutMapping("/updateFavorites/{idList}")
    public ResultVO updateFavorites(@PathVariable List<Integer> idList, @CookieValue("access_token") String accessToken) {
        log.debug("update favorites with ids: {}", idList);

        // 解析token获取userId
        //int userId = parseUserIdFromJWT(accessToken);
        Integer userId = JWTUtil.getUserId(accessToken);

        return ResultVO.success(articleService.updateFavorites(userId, idList));
    }

    @ApiOperation(value = "增加舆情信息关键词", notes = "增加舆情信息关键词(单条/批量)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "舆情ID", required = true, allowMultiple = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "keyword", value = "关键词列表", required = true, dataType = "string")
    })
    @PutMapping("/addKeyword/{idList}")
    public ResultVO addKeyword(@PathVariable List<Integer> idList, @RequestParam String keyword, @CookieValue("access_token") String accessToken) {
        log.debug("add keyword with ids: {}", idList);
        // 解析token获取userId
        //int userId = parseUserIdFromJWT(accessToken);
        Integer userId = JWTUtil.getUserId(accessToken);

        return ResultVO.success(articleService.addKeyword(userId, idList, keyword));
    }

    @ApiOperation(value = "删除舆情信息关键词", notes = "删除舆情信息关键词")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "舆情ID", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "keyword", value = "关键词", required = true, dataType = "string")
    })
    @PutMapping("/delKeyword/{id}")
    public ResultVO delKeyword(@PathVariable Integer id, @RequestParam String keyword, @CookieValue("access_token") String accessToken) {
        log.debug("delete keyword with id: {}", id);

        // 解析token获取userId
        //int userId = parseUserIdFromJWT(accessToken);
        Integer userId = JWTUtil.getUserId(accessToken);

        return ResultVO.success(articleService.delKeyword(userId, id, keyword));
    }

    @ApiOperation(value = "更新舆情等级", notes = "根据舆情id更新舆情等级")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "舆情ID", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "levelId", value = "舆情等级ID", required = true, dataType = "long")
    })
    @PutMapping(value = "/updateLevel/{id}")
    public ResultVO updateLevel(@PathVariable Integer id, @RequestParam Integer levelId, @CookieValue("access_token") String accessToken) {
        log.debug("update level with id: {}", id);
        articleService.updateLevel(id, levelId);

        // 解析token获取userId
        //int userId = parseUserIdFromJWT(accessToken);
        Integer userId = JWTUtil.getUserId(accessToken);

        return ResultVO.success(articleService.getArticleById(userId, id));
    }

    @ApiOperation(value = "更新舆情详情信息", notes = "根据舆情id更新舆情详情信息")
    @ApiImplicitParam(paramType = "query", name = "articleUpdateForm", value = "舆情详情信息", required = true, dataType = "ArticleUpdateForm")
    @PutMapping(value = "/updateArticle")
    public ResultVO updateArticle(@RequestBody ArticleUpdateForm articleUpdateForm, @CookieValue("access_token") String accessToken) {
        // 解析token获取userId
        //int userId = parseUserIdFromJWT(accessToken);
        Integer userId = JWTUtil.getUserId(accessToken);

        return ResultVO.success(articleService.updateArticle(userId, articleUpdateForm));
    }

}
