package com.dataint.monitor.service.impl;

import com.dataint.monitor.dao.IArticleLikeDao;
import com.dataint.monitor.dao.entity.ArticleLike;
import com.dataint.monitor.model.form.ArticleLikeForm;
import com.dataint.monitor.service.IArticleLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleLIkeServiceImpl  implements IArticleLikeService {
    @Autowired
    IArticleLikeDao articleLikeDao;
    @Override
    public ArticleLike saveArticleLike(ArticleLikeForm articleLikeForm) {
        ArticleLike articleLike = new ArticleLike();
        Long userId = articleLikeForm.getUserId();
        Long articleId = articleLikeForm.getArticleId();
        ArticleLike articleLikeId = articleLikeDao.getArticleLikeByArticleIdAndUserId(articleId, userId);
        if (articleLikeId != null) {
            Long id = articleLikeId.getId();
            articleLikeDao.deleteById(id);
        } else {
            if (articleLikeForm.getArticleId() != null) {
                articleLike.setArticleId(articleLikeForm.getArticleId());
            }
            if (articleLikeForm.getCreatedTime() != null) {
                articleLike.setCreatedTime(articleLikeForm.getCreatedTime());
            }
            if (articleLikeForm.getUserId() != null) {
                articleLike.setUserId(articleLikeForm.getUserId());
            }
        }
        articleLikeDao.save(articleLike);
        return articleLike;
    }
}
