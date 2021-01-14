package com.dataint.monitor.dao;

import com.dataint.monitor.dao.entity.ReportArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IReportArticleDao extends JpaRepository<ReportArticle, Long> {

    List<ReportArticle> findAllByArticleId(Long articleId);

    ReportArticle findByArticleIdAndReportType(Long articleId, String reportType);

    List<ReportArticle> findAllByUpdatedTimeBetweenAndReportTypeAndReportLevelId(Date updatedTimeStart, Date updatedTimeEnd, String reportType, Long levelId);
}
