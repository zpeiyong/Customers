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
        if (articleLikeForm.getArticleId()!=null){
            articleLike.setArticleId(articleLikeForm.getArticleId());
        }
        if (articleLikeForm.getCreatedTime()!=null){
            articleLike.setCreatedTime(articleLikeForm.getCreatedTime());
        }
        if (articleLikeForm.getUserId()!=null){
            articleLike.setUserId(articleLikeForm.getUserId());
        }
        articleLikeDao.save(articleLike);
        return  articleLike;
    }
}
