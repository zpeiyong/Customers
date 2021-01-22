package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.entity.ArticleDisease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IArticleDiseaseDao extends JpaRepository<ArticleDisease, Long> {

    @Query(value = "SELECT ad.disease_code AS diseaseCode, count(1) AS diseaseCnt " +
            "FROM article_disease ad " +
            "LEFT JOIN article a ON ad.article_id = a.id " +
            "WHERE a.gmt_crawl >= ?1 AND a.gmt_crawl <= ?2 AND " +
            "   (locate(?3, a.country_code) OR (locate(?3, ad.country_codes) AND ad.article_id IS NOT NULL)) " +
            "GROUP BY ad.disease_code " +
            "ORDER BY diseaseCnt DESC " +
            "LIMIT 10", nativeQuery = true)
    List<Object[]> queryDiseaseCntByCountry(Date startTime, Date endTime, String countryCode);

    @Query(value ="SELECT  ad.id FROM article_disease ad  where ad.article_id=?1" ,nativeQuery = true)
    List<Long> finIdByArticleId(Long articleId);

}
