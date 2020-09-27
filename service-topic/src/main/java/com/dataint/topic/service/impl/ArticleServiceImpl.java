package com.dataint.topic.service.impl;

import com.dataint.topic.db.entity.MediaType;
import com.dataint.topic.db.entity.TopicArticle;
import com.dataint.topic.db.dao.IArticleDao;
import com.dataint.topic.db.dao.IMediaTypeDao;
import com.dataint.topic.model.form.ArticleConditionForm;
import com.dataint.topic.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements IArticleService {
    @Autowired
    private IArticleDao articleDao;

    @Autowired
    private IMediaTypeDao mediaTypeDao;

    @Override
    public Object queryArticlesByCondition(ArticleConditionForm acReq) {

        Page<TopicArticle> result = articleDao.findAll(new Specification<TopicArticle>() {
            @Override
            public Predicate toPredicate(Root<TopicArticle> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();

                int keywordId = acReq.getKeywordId();
                int siteId = acReq.getSiteId();
                int mediaTypeId = acReq.getMediaTypeId();
                String sortOrder = acReq.getSortOrder();

                // 关键字
                if (keywordId != 0)
                    list.add(criteriaBuilder.equal(root.get("keywordId"), keywordId));

                // 来源
                if (siteId != 0)
                    list.add(criteriaBuilder.equal(root.get("siteId"), siteId));

                // 类型
                if (mediaTypeId != 0) {
                    if (mediaTypeId == 3) {
                        // 知名大V

                    } else {
                        // getAll 官方媒体/新闻媒体
                        List<MediaType> mediaTypeList = mediaTypeDao.getAllByMediaTypeId(mediaTypeId);

                        if (mediaTypeList != null && mediaTypeList.size() > 0) {
                            CriteriaBuilder.In<String> in = criteriaBuilder.in(root.get("author"));
//                            for (MediaType mediaType : mediaTypeList) {
//                                in.value(mediaType.getMediaTypeName());
//                            }

                            list.add(criteriaBuilder.not(in));
                        }
                    }
                }

                // 排序
                if ("asc".equals(sortOrder.toLowerCase()))
                    criteriaQuery.orderBy(criteriaBuilder.asc(root.get("gmtRelease")));
                else
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("gmtRelease")));

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, PageRequest.of(acReq.getCurrent()-1, acReq.getPageSize()));

        return result;
    }
}
