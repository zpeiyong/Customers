package com.dataint.monitor.controller;

import com.dataint.cloud.common.model.Constants;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.utils.JWTUtil;
import com.dataint.monitor.model.form.ArticleLikeForm;
import com.dataint.monitor.service.IArticleLikeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/articleLike")
@Slf4j
public class ArticleLikeController {
    @Autowired
    IArticleLikeService articleLikeService;
    @PostMapping(value = "/saveArticleLike")
    @ApiOperation(value = "点赞", notes = "增加ArticleLike数据")
    @ApiImplicitParam(paramType = "articleId", name = "articleId", value = "文章Id", required = true, dataType = "string")
    public ResultVO saveArticleLike(@RequestHeader(value = Constants.AUTHORIZE_ACCESS_TOKEN) String accessToken, ArticleLikeForm articleLikeForm){
        Long userId = JWTUtil.getUserId(accessToken);
        articleLikeForm.setUserId(userId);
        articleLikeForm.setCreatedTime(new Date());
        return ResultVO.success(articleLikeService.saveArticleLike(articleLikeForm));
    }

}
