package com.dataint.topic.controller;

import com.dataint.topic.model.ArticleConditionReq;
import com.dataint.topic.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/article")
public class ArticleController {
    @Autowired
    private IArticleService articleService;

    @GetMapping("/getArticleList")
    @ResponseBody
    public Object getArticleList(ArticleConditionReq acReq) {

        return articleService.queryArticlesByCondition(acReq);
    }
}
