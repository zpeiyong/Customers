package com.dataint.topic.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.topic.model.form.ArticleConditionForm;
import com.dataint.topic.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/article")
public class ArticleController {
    @Autowired
    private IArticleService articleService;

    @GetMapping("/getArticleList")
    @ResponseBody
    public ResultVO getArticleList(ArticleConditionForm acReq) {

        return ResultVO.success(articleService.queryArticlesByCondition(acReq));
    }
}
