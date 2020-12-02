package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.IMapCountry;
import com.dataint.service.datapack.db.entity.DiseaseCountryCase;
import com.dataint.service.datapack.model.vo.DiseaseCountryCaseVO;
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
            "where dcc.diseaseId=t2.disease_id and dcc.statisticDate=t2.statistic_date " +
            "order by t2.confirm_total desc", nativeQuery = true)
    List<Map<String,Object>> getLatestDiseaseCasesByCountry(Long countryId);
}
