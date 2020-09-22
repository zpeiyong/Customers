package com.dataint.topic.service;

import com.dataint.topic.model.ArticleConditionReq;


public interface IArticleService {

    Object queryArticlesByCondition(ArticleConditionReq acReq);

//    Object updateArticle(UpdateArticleReq poReq);
//
//    Map<String, Object> queryByPage(PageParam pageParam);
//
//    ArticleVO queryById(Integer articleId);
//
//    List<ArticleVO> queryArticleByIds(List<Integer> articleIds);
}
