package com.dataint.topic.db.repository;

import com.dataint.topic.db.IBaseInteraction;
import com.dataint.topic.db.entity.DynamicData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DynamicDataRepository extends JpaRepository<DynamicData, Integer> {

    @Query(value = "SELECT a.keyword_id AS keywordId, any_value(SUM(c.forwardCnt)) AS forwardCnt, " +
            "any_value(SUM(c.commentCnt)) AS commentCnt, any_value(SUM(c.likeCnt)) AS likeCnt " +
            "FROM article a  LEFT JOIN " +
            "(SELECT d.article_id AS articleID, any_value(d.forward_cnt) AS forwardCnt, any_value(d.comment_cnt) AS commentCnt, any_value(d.like_cnt) AS likeCnt " +
            "FROM (SELECT * FROM dynamic_data ORDER BY gmt_crawl DESC) d " +
            "GROUP BY articleId) c " +
            "ON a.article_id=c.articleId " +
            "WHERE gmt_release >= ?1 AND gmt_release <= ?2 " +
            "GROUP BY keywordId", nativeQuery = true)
    List<IBaseInteraction> countInteractions(String startTime, String endTime);
}
