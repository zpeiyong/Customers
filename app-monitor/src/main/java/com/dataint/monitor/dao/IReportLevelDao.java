package com.dataint.monitor.dao;

import com.dataint.monitor.dao.entity.ReportLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReportLevelDao extends JpaRepository<ReportLevel, Long> {

    List<ReportLevel> findAllByOrderBySort();

}
