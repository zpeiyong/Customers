package com.dataint.monitor.dao;

import com.dataint.monitor.dao.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IReportDao extends JpaRepository<Report, Long> {

    @Query(value = "from Report r " +
            "where r.gmtStart >= ?1 and r.gmtEnd <= ?2 and r.reportType = ?3")
    Page<Report> findByGmtStartAndGmtEndAndReportType(Date gmtStart, Date gmtEnd, String reportType, Pageable pageable);

    List<Report> findByGmtStartAndGmtEndAndReportType(Date gmtStart, Date gmtEnd, String reportType);

    Page<Report> findAllByReportType(String reportType, Pageable pageable);

    Page<Report> findAllByReportTypeAndTitleIn(String reportType, List<String> titleList, Pageable pageable);

}
