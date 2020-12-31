package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.IDayDate;
import com.dataint.service.datapack.db.IMapCountry;
import com.dataint.service.datapack.db.entity.DiseaseCountryCase;
import com.dataint.service.datapack.model.vo.DiseaseCountryCaseVO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface IDiseaseCountryCaseDao extends JpaRepository<DiseaseCountryCase, Long> {

    @Query(value = "select distinct dcc.countryId as countryId, c.nameCn as countryNameCn " +
            "from DiseaseCountryCase dcc " +
            "left join Country c on c.id = dcc.countryId")
    List<IMapCountry> getMapCountryList();

//    @Query(value = "select new com.dataint.service.datapack.model.vo.DiseaseCountryCaseVO(dcc, fd.nameCn, c.nameCn) " +
//            "from DiseaseCountryCase dcc " +
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

    @Query(value = "select date_format(statistic_date, '%Y-%m-%d') as day, confirm_add as cnt " +
            "from disease_country_case " +
            "where disease_id = ?1 and statistic_date >= DATE_SUB(CURDATE(), INTERVAL ?2 DAY)", nativeQuery = true)
    List<IDayDate> getDailyConFirmedAddByDiseaseId(Long diseaseId, int i);

    @Query(value = "select date_format(statistic_date, '%Y-%m-%d') as day, cure_add as cnt " +
            "from disease_country_case " +
            "where disease_id = ?1 and statistic_date >= DATE_SUB(CURDATE(), INTERVAL ?2 DAY)", nativeQuery = true)
    List<IDayDate> getDailyCuredAddByDiseaseId(Long diseaseId, int i);

    @Query(value = "select date_format(statistic_date, '%Y-%m-%d') as day, death_add as cnt " +
            "from disease_country_case " +
            "where disease_id = ?1 and statistic_date >= DATE_SUB(CURDATE(), INTERVAL ?2 DAY)", nativeQuery = true)
    List<IDayDate> getDailyDeathAddByDiseaseId(Long diseaseId, int i);

    @Query(value = "select new map(c.nameCn, dcc.deathTotal * 10000  / dcc.confirmTotal) " +
            "from DiseaseCountryCase dcc " +
            "left join Country c on dcc.countryId = c.id " +
            "where dcc.diseaseId = ?1 and dcc.statisticDate = ?2 " +
            "order by dcc.deathTotal/dcc.confirmTotal desc")
    List<Map<String, Object>> getDeathCntByDiseaseIdAndStatisticDate(Long diseaseId, Date yesStartDate, PageRequest articleTotal);

    @Query(value = "select new map(c.nameCn, dcc.confirmAdd) " +
            "from DiseaseCountryCase dcc " +
            "left join Country c on dcc.countryId = c.id " +
            "where dcc.diseaseId = ?1 and dcc.statisticDate = ?2 " +
            "order by dcc.confirmAdd desc")
    List<Map<String, Object>> getConfirmedCntByDiseaseIdAndStatisticDate(Long diseaseId, Date yesStartDate, PageRequest of);

    @Query(value = "select new map(c.nameCn, dcc.cureTotal * 10000 / dcc.confirmTotal) " +
            "from DiseaseCountryCase dcc " +
            "left join Country c on dcc.countryId = c.id " +
            "where dcc.diseaseId = ?1 and dcc.statisticDate = ?2 " +
            "order by dcc.cureTotal/dcc.confirmTotal desc")
    List<Map<String, Object>> getCuredCntByDiseaseIdAndStatisticDate(Long diseaseId, Date yesStartDate, PageRequest of);
}
