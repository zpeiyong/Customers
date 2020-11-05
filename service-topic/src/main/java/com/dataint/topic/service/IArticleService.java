package com.dataint.topic.service;

import com.dataint.topic.db.entity.TopicArticle;
import com.dataint.topic.model.form.ArticleConditionForm;


public interface IArticleService {

    Object queryArticlesByCondition(ArticleConditionForm acReq);

    /**
     * 根据id查询专题详情
     * @param id
     */
    Object getArticleById(Long id);

//    Object updateArticle(UpdateArticleReq poReq);
//
//    Map<String, Object> queryByPage(PageParam pageParam);
//
//    ArticleVO queryById(Integer articleId);
//
//    List<ArticleVO> queryArticleByIds(List<Integer> articleIds);
}
