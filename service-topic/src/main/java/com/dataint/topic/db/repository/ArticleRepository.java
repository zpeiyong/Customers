package com.dataint.topic.db.repository;

import com.dataint.topic.db.IBaseArticle;
import com.dataint.topic.db.entity.Topic;
import com.dataint.topic.db.entity.TopicArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<TopicArticle, Integer>, JpaSpecificationExecutor<TopicArticle> {

    Page<TopicArticle> findAll(Pageable pageable);

    @Query(value = "SELECT keyword_id AS keywordId, COUNT(1) AS articleCnt, COUNT(DISTINCT author) AS websiteCnt " +
            "FROM article " +
            "WHERE gmt_release >= ?1 AND gmt_release <= ?2 " +
            "GROUP BY keywordId", nativeQuery = true)
    List<IBaseArticle> countArticlesAndWebsite(String startTime, String endTime);

    @Query(value = "SELECT DISTINCT keyword_id AS keyword_id " +
            "FROM article " +
            "WHERE gmt_release >= ?1 AND gmt_release <= ?2", nativeQuery = true)
    List<Integer> getKeywordIdList(String startTime, String endTime);

    TopicArticle findTop1ByKeywordIdOrderByGmtCrawl(int keywordId);

    @Query(value = "FROM TopicArticle a WHERE a.keywordId = ?1 AND a.gmtCrawl >= ?2 AND a.gmtCrawl <= ?3")
    Page<TopicArticle> getArticlesByKeywordIdAndGmtCrawlBetween(int keywordId, Date startTime, Date endTime, Pageable pageable);

    TopicArticle getArticleByEvent(Topic event);

}
