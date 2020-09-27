package com.dataint.topic.service;

import com.dataint.topic.model.form.ArticleConditionForm;


public interface IArticleService {

    Object queryArticlesByCondition(ArticleConditionForm acReq);

//    Object updateArticle(UpdateArticleReq poReq);
//
//    Map<String, Object> queryByPage(PageParam pageParam);
//
//    ArticleVO queryById(Integer articleId);
//
//    List<ArticleVO> queryArticleByIds(List<Integer> articleIds);
}
