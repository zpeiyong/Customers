package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.IDayDate;
import com.dataint.service.datapack.db.entity.StatisticBasic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStatisticBasicDao extends JpaRepository<StatisticBasic, Long> {

    StatisticBasic getTopByDiseaseIdOrderByStatisticDateDesc(Long diseaseId);

    @Query(value = "select date_format(statistic_date, '%Y-%m-%d') as day, country_add as cnt " +
            "from statistic_basic " +
            "where disease_id = ?1 and statistic_date >= DATE_SUB(CURDATE(), INTERVAL ?2 DAY)", nativeQuery = true)
    List<IDayDate> getDailyCountryAddByDiseaseId(Long diseaseId, Integer nDays);

    @Query(value = "select date_format(statistic_date, '%Y-%m-%d') as day, event_add as cnt " +
            "from statistic_basic " +
            "where disease_id = ?1 and statistic_date >= DATE_SUB(CURDATE(), INTERVAL ?2 DAY)", nativeQuery = true)
    List<IDayDate> getDailyEventAddByDiseaseId(Long diseaseId, Integer nDays);

    @Query(value = "select date_format(statistic_date, '%Y-%m-%d') as day, article_add as cnt " +
            "from statistic_basic " +
            "where disease_id = ?1 and statistic_date >= DATE_SUB(CURDATE(), INTERVAL ?2 DAY)", nativeQuery = true)
    List<IDayDate> getDailyArticleAddByDiseaseId(Long diseaseId, Integer nDays);
}
