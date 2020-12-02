package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.entity.StatisticGlobalRisk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IStatisticGlobalRiskDao extends JpaRepository<StatisticGlobalRisk, Long> {

    @Query(value = "select date_format(statistic_date, '%Y-%m-%d') as statisticDate, global_risk_score as globalRiskScore " +
            "from statistic_global_risk " +
            "where statistic_date >= DATE_SUB(CURDATE(), INTERVAL ?1 DAY)", nativeQuery = true)
    List<Map<String, Object>> getIntervalGlobalRisks(Integer days);
}
