package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.IDayDate;
import com.dataint.service.datapack.db.entity.DiseaseCountryPO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface IDiseaseCountryPODao extends JpaRepository<DiseaseCountryPO, Long> {

    @Query(value = "select new map(c.nameCn, dcp.riskScore) " +
            "from DiseaseCountryPO dcp " +
            "left join Country c on dcp.countryId = c.id " +
            "where dcp.diseaseId = ?1 and dcp.statisticDate = ?2")
    List<Map<String, Object>> getRiskRankByDiseaseIdAndStatisticDate(Long diseaseId, Date statisticDate, Pageable pageable);

    @Query(value = "select new map(c.nameCn, dcp.eventToTal) " +
            "from DiseaseCountryPO dcp " +
            "left join Country c on dcp.countryId = c.id " +
            "where dcp.diseaseId = ?1 and dcp.statisticDate = ?2")
    List<Map<String, Object>> getEventCntByDiseaseIdAndStatisticDate(Long diseaseId, Date statisticDate, Pageable pageable);

    @Query(value = "select new map(c.nameCn, dcp.articleTotal) " +
            "from DiseaseCountryPO dcp " +
            "left join Country c on dcp.countryId = c.id " +
            "where dcp.diseaseId = ?1 and dcp.statisticDate = ?2")
    List<Map<String, Object>> getArticleCntByDiseaseIdAndStatisticDate(Long diseaseId, Date statisticDate, Pageable pageable);

    @Query(value = "SELECT SUM(article_add_official) as officialAdd, SUM(article_add_media) as mediaAdd, statistic_date as statisticDate FROM disease_country_po " +
            "GROUP BY statistic_date,disease_id " +
            "HAVING statistic_date > DATE_SUB(?2,INTERVAL ?3 DAY) " +
            "AND statistic_date <= ?2 " +
            "AND disease_id =?1",nativeQuery = true)
     List<Map<String,Object>> getArticleAddGroupByDiseaseIdAndStatisticDate(Long diseaseId, String DateStr, int i);

}
