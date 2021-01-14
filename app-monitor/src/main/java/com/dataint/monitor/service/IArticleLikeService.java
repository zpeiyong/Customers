package com.dataint.monitor.service;


import com.dataint.monitor.dao.entity.ArticleLike;
import com.dataint.monitor.model.form.ArticleLikeForm;

public interface IArticleLikeService {
    ArticleLike saveArticleLike(ArticleLikeForm  articleLikeForm);
}
