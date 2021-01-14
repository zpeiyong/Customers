package com.dataint.monitor.dao;

import com.dataint.monitor.dao.entity.ArticleReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IArticleReportDao extends JpaRepository<ArticleReport, Long> {

    List<ArticleReport> findAllByTitleLikeOrSummaryLikeOrderByReportTitleDesc(String title, String summary);
    
    boolean existsByArticleId(Long articleId);
}
