package com.dataint.topic.service;

import com.dataint.topic.model.ArticleConditionReq;
import com.dataint.topic.common.exception.ThinventBaseException;

public interface IArticleService {

    Object queryArticlesByCondition(ArticleConditionReq acReq) throws ThinventBaseException;

//    Object updateArticle(UpdateArticleReq poReq);
//
//    Map<String, Object> queryByPage(PageParam pageParam);
//
//    ArticleVO queryById(Integer articleId);
//
//    List<ArticleVO> queryArticleByIds(List<Integer> articleIds);
}
