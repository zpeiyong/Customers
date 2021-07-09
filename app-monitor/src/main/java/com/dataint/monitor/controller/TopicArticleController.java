package com.dataint.monitor.controller;

import com.dataint.monitor.adapt.ITopicArticleAdapt;
import com.dataint.monitor.model.form.ArticleConditionForm;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "/topicArticle")
public class TopicArticleController {

    @Autowired
    private ITopicArticleAdapt topicArticleAdapt;

    /**
     * 查询所有专题文章
     *
     * @auther: Tim_Huo
     * @param: ArticleConditionForm
     * @return: ResultVO
     * @date: 2020/10/27 10:52 上午
     */
    @GetMapping("/getArticleList")
    public Object getArticleList(ArticleConditionForm acReq) {

        return topicArticleAdapt.queryArticlesByCondition(acReq);
    }

    /**
     * 根据id查询专题文章详情
     *
     * @auther: Tim_Huo
     * @param: id
     * @return:
     * @date: 2020/11/5 4:11 下午
     */
    @GetMapping("/getArticleById")
    public Object getArticleById(@RequestParam(value = "id", required = true) Long id) {

        return topicArticleAdapt.getArticleById(id);
    }

    @ApiOperation(value = "根据时间查询疫情总体分布", notes = "根据时间查询疫情总体分布")
    @RequestMapping("/getPopularFeelingsTj")
    public Object getPopularFeelingsTj(@RequestParam String gmtDate) {
//        log.debug("get all keyword by topicId");

        return topicArticleAdapt.getPopularFeelingsTj(gmtDate);
    }

}