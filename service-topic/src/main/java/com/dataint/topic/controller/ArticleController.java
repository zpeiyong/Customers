package com.dataint.topic.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.topic.model.form.ArticleConditionForm;
import com.dataint.topic.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 专题文章
 *
 * @auther: Tim_Huo
 * @date: 2020/10/26 5:51 下午
 */
@RestController
@RequestMapping(value = "/article")
public class ArticleController {
    @Autowired
    private IArticleService articleService;

    /**
     * 查询所有专题文章
     *
     * @auther: Tim_Huo
     * @param: ArticleConditionForm
     * @return: ResultVO
     * @date: 2020/10/27 10:52 上午
     */
    @GetMapping("/getArticleList")
    public ResultVO getArticleList(ArticleConditionForm acReq) {

        return ResultVO.success(articleService.queryArticlesByCondition(acReq));
    }
}
