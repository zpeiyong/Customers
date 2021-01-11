package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.IDayDate;
import com.dataint.service.datapack.db.IMapCountry;
import com.dataint.service.datapack.db.entity.DiseaseCountryCase;
import com.dataint.service.datapack.model.vo.DiseaseCountryCaseVO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface IDiseaseCountryCaseDao extends JpaRepository<DiseaseCountryCase, Long>, JpaSpecificationExecutor<DiseaseCountryCase> {

    @Query(value = "select distinct dcc.countryId as countryId, c.nameCn as countryNameCn " +
            "from DiseaseCountryCase dcc " +
            "left join Country c on c.id = dcc.countryId")
    List<IMapCountry> getMapCountryList();

//    @Query(value = "select new com.dataint.service.datapack.model.vo.DiseaseCountryCaseVO(dcc, fd.nameCn, c.nameCn) " +
//            "from DiseaseCountryCase1 dcc " +
//            "left join FocusDisease fd on dcc.diseaseId = fd.id " +
//            "left join Country c on dcc.countryId = c.id " +
//            "where dcc.statisticDate = ?1 and dcc.countryId = ?2")
//    Page<DiseaseCountryCaseVO> getCasesByCountryId(Date statisticDate, Long countryId, Pageable pageable);

    @Query(value = "select new com.dataint.service.datapack.model.vo.DiseaseCountryCaseVO(dcc, fd.nameCn, c.nameCn) " +
            "from DiseaseCountryCase dcc " +
            "left join FocusDisease fd on dcc.diseaseId = fd.id " +
            "left join Country c on dcc.countryId = c.id " +
            "where dcc.statisticDate = ?1 and dcc.countryId = ?2 and dcc.diseaseId = ?3")
    DiseaseCountryCaseVO getCasesByCountryIdAndDiseaseId(Date statisticDate, Long countryId, Long diseaseId);

    @Query(value = "select dcc.diseaseId, fd.name_cn as diseaseNameCn, t2.country_id, c.name_cn as countryNameCn, dcc.statisticDate, " +
            "t2.period_start, t2.period_end, t2.confirm_total, t2.confirm_add, t2.death_total, t2.death_add, t2.cure_total, t2.cure_add, fd.show_type " +
            "from ( " +
            "   select t1.disease_id as diseaseId, MAX(t1.statistic_date) as statisticDate " +
            "   from disease_country_case t1 " +
            "   where t1.country_id = ?1 " +
            "   group by diseaseId) dcc " +
            "inner join disease_country_case t2 " +
            "left join focus_diseases fd on dcc.diseaseId = fd.id " +
            "left join country c on t2.country_id = c.id " +
            "where dcc.diseaseId=t2.disease_id and dcc.statisticDate=t2.statistic_date and t2.country_id = ?1 " +
            "order by t2.confirm_total desc", nativeQuery = true)
    List<Map<String,Object>> getLatestDiseaseCasesByCountry(Long countryId);

    @Query(value = "SELECT * FROM disease_country_case d " +
            "RIGHT JOIN (SELECT country_name_cn  FROM ( " +
            "SELECT * FROM disease_country_case WHERE " +
            "statistic_date > DATE_SUB(?3,INTERVAL ?4 DAY) AND statistic_date <= ?3 " +
            ")as dis GROUP BY country_name_cn,disease_id " +
            "HAVING disease_id = ?1 " +
            "ORDER BY MAX(confirm_add) DESC LIMIT ?2) d1  " +
            "on d.country_name_cn = d1.country_name_cn " +
            "ORDER BY d.country_name_cn DESC;", nativeQuery = true)
    List<DiseaseCountryCase> getDailyConFirmedAddByDiseaseId(Long diseaseId,int limit,String dateStr, int i);

    List<DiseaseCountryCase>  findByDiseaseIdAndCountryNameCnAndPeriodStart(Long diseaseId,String countryNameCn,Date periodStart);

    List<DiseaseCountryCase>  findByDiseaseIdAndShowTypeAndPeriodStart(Long diseaseId,String showType,Date periodStart);

    @Query(value = "SELECT * FROM disease_country_case d " +
            "RIGHT JOIN (SELECT country_name_cn  FROM ( " +
            "SELECT * FROM disease_country_case WHERE " +
            "statistic_date > DATE_SUB(?3,INTERVAL ?4 DAY) AND statistic_date <= ?3 " +
            ")as dis GROUP BY country_name_cn,disease_id " +
            "HAVING disease_id = ?1 " +
            "ORDER BY MAX(cure_add) DESC LIMIT ?2) d1  " +
            "on d.country_name_cn = d1.country_name_cn " +
            "ORDER BY d.country_name_cn DESC;", nativeQuery = true)
    List<DiseaseCountryCase> getDailyCuredAddByDiseaseId(Long diseaseId, int limit,String dateStr, int i);

    @Query(value = "SELECT * FROM disease_country_case d " +
            "RIGHT JOIN (SELECT country_name_cn  FROM ( " +
            "SELECT * FROM disease_country_case WHERE " +
            "statistic_date > DATE_SUB(?3,INTERVAL ?4 DAY) AND statistic_date <= ?3 " +
            ")as dis GROUP BY country_name_cn,disease_id " +
            "HAVING disease_id = ?1 " +
            "ORDER BY MAX(death_add) DESC LIMIT ?2) d1  " +
            "on d.country_name_cn = d1.country_name_cn " +
            "ORDER BY d.country_name_cn DESC;", nativeQuery = true)
    List<DiseaseCountryCase> getDailyDeathAddByDiseaseId(Long diseaseId, int limit,String dateStr, int i);

    @Query(value = "select new map(dcc.countryNameCn, dcc.deathTotal * 10000  / dcc.confirmTotal) " +
            "from DiseaseCountryCase dcc " +
            "where dcc.diseaseId = ?1 and dcc.statisticDate = ?2 " +
            "order by dcc.deathTotal/dcc.confirmTotal desc")
    List<Map<String, Object>> getDeathCntByDiseaseIdAndStatisticDate(Long diseaseId, Date yesStartDate, PageRequest articleTotal);

    @Query(value = "select new map(dcc.countryNameCn, dcc.confirmAdd) " +
            "from DiseaseCountryCase dcc " +
            "where dcc.diseaseId = ?1 and dcc.statisticDate = ?2 " +
            "order by dcc.confirmAdd desc")
    List<Map<String, Object>> getConfirmedCntByDiseaseIdAndStatisticDate(Long diseaseId, Date yesStartDate, PageRequest of);

    @Query(value = "select new map(dcc.countryNameCn, dcc.cureTotal * 10000 / dcc.confirmTotal) " +
            "from DiseaseCountryCase dcc " +
            "where dcc.diseaseId = ?1 and dcc.statisticDate = ?2 " +
            "order by dcc.cureTotal/dcc.confirmTotal desc")
    List<Map<String, Object>> getCuredCntByDiseaseIdAndStatisticDate(Long diseaseId, Date yesStartDate, PageRequest of);
}
