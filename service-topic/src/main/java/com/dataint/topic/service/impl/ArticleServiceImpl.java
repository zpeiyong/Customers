package com.dataint.topic.service.impl;

import com.dataint.cloud.common.model.Pagination;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.topic.db.dao.IArticleDao;
import com.dataint.topic.db.dao.IMediaTypeDao;
import com.dataint.topic.db.entity.TopicArticle;
import com.dataint.topic.model.form.ArticleConditionForm;
import com.dataint.topic.model.vo.TopicArticleVO;
import com.dataint.topic.service.IArticleService;
import lombok.extern.slf4j.Slf4j;
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
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleServiceImpl implements IArticleService {
    @Autowired
    private IArticleDao articleDao;

    @Autowired
    private IMediaTypeDao mediaTypeDao;

    @Override
    public ResultVO queryArticlesByCondition(ArticleConditionForm acReq) {

        Page<TopicArticle> topicArticlePage = articleDao.findAll(new Specification<TopicArticle>() {
            @Override
            public Predicate toPredicate(Root<TopicArticle> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();

                Long keywordId = acReq.getKeywordId();
                Long topicId = acReq.getTopicId();
                ArrayList<Long> siteIdList = acReq.getSiteIdList();
                ArrayList<Long> mediaTypeIdList = acReq.getMediaTypeIdList();
                String sortOrder = acReq.getSortOrder();

                //专题id
                if(topicId != null && topicId > 0) {
                    list.add(criteriaBuilder.equal(root.get("id"), topicId));
                } else {
                    // 关键字
                    if (keywordId != null && keywordId >  0)
                        list.add(criteriaBuilder.equal(root.get("keywordId"), keywordId));
                }

                // 来源
                if (siteIdList != null && siteIdList.size() != 0){
                    CriteriaBuilder.In<Long> siteId = criteriaBuilder.in(root.get("siteId"));
                    for (Long id : siteIdList) {
                        siteId.value(id);
                    }
                    list.add(criteriaBuilder.and(siteId));
                }

                // 类型
                // getAll 官方媒体/新闻媒体
                if (mediaTypeIdList != null && mediaTypeIdList.size() > 0) {
                    CriteriaBuilder.In<Long> mediaTypeId = criteriaBuilder.in(root.get("mediaTypeId"));
                    for (Long id : mediaTypeIdList) {
                        mediaTypeId.value(id);
                    }
                    list.add(criteriaBuilder.and(mediaTypeId));
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

        // covert to VO
        List<TopicArticleVO> topicArticleVOList = topicArticlePage.getContent().stream().map(TopicArticleVO::new).collect(Collectors.toList());

        Pagination pagination = new Pagination();
        pagination.setCurrent(acReq.getCurrent());
        pagination.setPageSize(acReq.getPageSize());
        pagination.setTotal(topicArticlePage.getTotalElements());

        return ResultVO.success(topicArticleVOList, pagination);
    }

    @Override
    public ResultVO getArticleById(Long id) {
        if(id == 0) {
            log.info("传入Id为空，请重试：{}", id);
            return null;
        }

        TopicArticle topicArticle = articleDao.findOneById(id);

        return ResultVO.success(new TopicArticleVO(topicArticle));
    }


}
