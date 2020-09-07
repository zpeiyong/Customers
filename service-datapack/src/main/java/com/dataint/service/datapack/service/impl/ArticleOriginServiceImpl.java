package com.dataint.service.datapack.service.impl;

import com.dataint.service.datapack.dao.IArticleOriginDao;
import com.dataint.service.datapack.dao.entity.ArticleOrigin;
import com.dataint.service.datapack.model.ArticleOriginVO;
import com.dataint.service.datapack.service.IArticleOriginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleOriginServiceImpl implements IArticleOriginService {

    @Autowired
    private IArticleOriginDao articleOriginDao;

    @Override
    public ArticleOriginVO get(Integer id) {
        Optional<ArticleOrigin> articleOriginOpt = articleOriginDao.findById(id);

        if (articleOriginOpt.isPresent())
            return new ArticleOriginVO(articleOriginOpt.get());
        else
            return new ArticleOriginVO();
    }
}
