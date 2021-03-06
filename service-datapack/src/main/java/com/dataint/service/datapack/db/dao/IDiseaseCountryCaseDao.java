package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.IMapCountry;
import com.dataint.service.datapack.db.entity.DiseaseCountryCase;
import com.dataint.service.datapack.model.vo.DiseaseCountryCaseVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "select t2.id, dcc.diseaseId, fd.name_cn as diseaseNameCn, t2.country_id, c.name_cn as countryNameCn, dcc.statisticDate, " +
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
            "RIGHT JOIN (SELECT country_name_cn " +
            "   FROM (SELECT * FROM disease_country_case " +
            "       WHERE statistic_date > DATE_SUB(?3,INTERVAL ?4 DAY) AND statistic_date <= ?3)as dis " +
            "   GROUP BY country_name_cn,disease_id " +
            "   HAVING disease_id = ?1 " +
            "   ORDER BY MAX(confirm_add) DESC LIMIT ?2) d1  " +
            "ON d.country_name_cn = d1.country_name_cn " +
            "WHERE statistic_date > DATE_SUB(?3,INTERVAL ?4 DAY) AND statistic_date <= ?3 " +
            "ORDER BY d.country_name_cn DESC;", nativeQuery = true)
    List<DiseaseCountryCase> getDailyConFirmedAddByDiseaseId(Long diseaseId,int limit,String dateStr, int i);

    //????????????????????? ????????????????????????
    List<DiseaseCountryCase>  findByDiseaseIdAndCountryIdAndPeriodStart(Long diseaseId,Long countryId,Date periodStart);

    List<DiseaseCountryCase>  findByDiseaseIdAndShowTypeAndPeriodStart(Long diseaseId,String showType,Date periodStart);

    @Query(value = "SELECT * FROM disease_country_case d " +
            "RIGHT JOIN (SELECT country_name_cn " +
            "   FROM (SELECT * FROM disease_country_case " +
            "       WHERE statistic_date > DATE_SUB(?3,INTERVAL ?4 DAY) AND statistic_date <= ?3)as dis " +
            "   GROUP BY country_name_cn,disease_id " +
            "   HAVING disease_id = ?1 " +
            "   ORDER BY MAX(cure_add) DESC LIMIT ?2) d1  " +
            "ON d.country_name_cn = d1.country_name_cn " +
            "WHERE statistic_date > DATE_SUB(?3,INTERVAL ?4 DAY) AND statistic_date <= ?3 " +
            "ORDER BY d.country_name_cn DESC;", nativeQuery = true)
    List<DiseaseCountryCase> getDailyCuredAddByDiseaseId(Long diseaseId, int limit,String dateStr, int i);

    @Query(value = "SELECT * FROM disease_country_case d " +
            "RIGHT JOIN (SELECT country_name_cn " +
            "   FROM (SELECT * FROM disease_country_case " +
            "       WHERE statistic_date > DATE_SUB(?3,INTERVAL ?4 DAY) AND statistic_date <= ?3)as dis " +
            "   GROUP BY country_name_cn,disease_id " +
            "   HAVING disease_id = ?1 " +
            "   ORDER BY MAX(death_add) DESC LIMIT ?2) d1  " +
            "ON d.country_name_cn = d1.country_name_cn " +
            "WHERE statistic_date > DATE_SUB(?3,INTERVAL ?4 DAY) AND statistic_date <= ?3 " +
            "ORDER BY d.country_name_cn DESC;", nativeQuery = true)
    List<DiseaseCountryCase> getDailyDeathAddByDiseaseId(Long diseaseId, int limit,String dateStr, int i);

    @Query(value = "select new map(dcc.countryNameCn, dcc.deathTotal * 10000  / dcc.confirmTotal)" +
            " from DiseaseCountryCase dcc " +
            " where dcc.diseaseId = ?1 and dcc.statisticDate = ?2 " +
            " order by dcc.deathTotal/dcc.confirmTotal  desc")
    Page<Map<String, Object>> getDeathCntByDiseaseIdAndStatisticDate(Long diseaseId, Date yesStartDate, PageRequest articleTotal);

    @Query(value = "select new map(dcc.countryNameCn, dcc.confirmAdd) " +
            " from DiseaseCountryCase dcc " +
            " where dcc.diseaseId = ?1 and dcc.statisticDate = ?2 " +
            " order by dcc.confirmAdd desc")
    List<Map<String, Object>> getConfirmedCntByDiseaseIdAndStatisticDate(Long diseaseId, Date yesStartDate, PageRequest of);

    @Query(value = "select new map(dcc.countryNameCn, dcc.cureTotal * 10000 / dcc.confirmTotal) " +
            " from DiseaseCountryCase dcc " +
            " where dcc.diseaseId = ?1 and dcc.statisticDate = ?2 " +
            " order by dcc.cureTotal / dcc.confirmTotal  desc")
    List<Map<String, Object>> getCuredCntByDiseaseIdAndStatisticDate(Long diseaseId, Date yesStartDate, PageRequest of);

    DiseaseCountryCase findTopByDiseaseIdAndCountryIdOrderByStatisticDateDesc(Long diseaseId, Long countryId);

    @Query(nativeQuery = true,value="select dcc2.country_id,max(dcc2.country_name_cn) country_name_cn,sum(dcc2.confirm_total) confirm_total\n" +
            " from disease_country_case dcc2 where dcc2.id in (\n" +
            " select max(id) from disease_country_case dcc where (dcc.period_end,dcc.country_id) in (\n" +
            " select max(dcc1.period_end),dcc1.country_id from disease_country_case  dcc1 GROUP BY dcc1.country_id) group by dcc.country_id)\n" +

            " group by dcc2.country_id\n" +
            " order by confirm_total desc\n" +
            " limit 0,10")
    List<Map<String,Object>> getCountryDataTj();

    @Query(nativeQuery = true,value="select  max(dcc2.disease_id) as disease_id,max(dcc2.disease_name_cn) as disease_name_cn ,SUM(dcc2.confirm_total ) as confirm_total\n" +
            " from disease_country_case dcc2 where dcc2.id in (\n" +
            " select max(id) from disease_country_case dcc where (dcc.period_end,dcc.disease_id,dcc.country_id) in (\n" +
            " select max(dcc1.period_end),dcc1.disease_id,dcc1.country_id from disease_country_case  dcc1 where dcc1.confirm_total is not null  GROUP BY dcc1.disease_id,dcc1.country_id) group by dcc.disease_id,dcc.country_id)\n" +
            "-- and dcc2.country_id = 2\n" +
            " GROUP BY dcc2.disease_id\n" +
            " order by confirm_total desc\n" +
            " limit 0,10")
    List<Map<String,Object>> getDiseaseDataTj();


    @Query(value = "select dcc.country_id,dcc.disease_id,sum(dcc.confirm_add) confirm_add,sum(dcc.death_add) death_add, week(dcc.period_end) week_num\n" +
            " from disease_country_case dcc \n" +
            " where dcc.show_type='daily' and dcc.disease_id=?1 and dcc.country_id=?2\n" +
            " \n" +
            " and year(now()) - year(dcc.period_end) = ?4  and (week(now()) - week(dcc.period_end))  between 0 and ?3\t\n" +
            " group by week(dcc.period_end) ,dcc.country_name_cn\n" +
            " \n" +
            " order by week_num" ,nativeQuery = true)
    List<Map<String,String>> getForCountryRisk1(int diseaseId,int countryId,int week,int year);

    @Query("from DiseaseCountryCase dcc where dcc.diseaseId=?1 and  dcc.countryId=?2 order by dcc.periodEnd desc")
    List<DiseaseCountryCase> getForCountryDiseaseAdd(long diseaseId, long countryId, Pageable page);

    @Query(value="from select \n" +
            " (select max(dcc.confirm_total) from disease_country_case dcc where dcc.disease_id=?1 and dcc.country_id=?2) a1,\n" +
            " (select max(dcc.confirm_total) from disease_country_case dcc) a,\n" +
            " (select dcc.confirm_add from disease_country_case dcc  where dcc.disease_id=?1 and dcc.country_id=?2 order by dcc.period_end,id limit 0,1) b1,\n" +
            " (select max(dcc.confirm_add) from disease_country_case dcc) b,\n" +
            " (select count(*) from disease_country_case dcc  where dcc.disease_id=?1 and dcc.country_id=?2 and dcc.period_end is not null and dcc.confirm_add=0 order by dcc.period_end,id desc) c1,\n" +
            " (select max(c) from (\n" +
            " select count(*) c from disease_country_case dcc where  dcc.period_end is not null and dcc.confirm_add=0  group by dcc.country_id,dcc.disease_id) temp) c ,\n" +
            " (select count(*) from (\n" +
            " select dcc.country_id from disease_country_case dcc where dcc.disease_id=?1 group by dcc.country_id) temp) d1,\n" +
            " (select count(*) from country) d,\n" +
            " (select dcc.death_total / dcc.confirm_total \n" +
            " from disease_country_case dcc \n" +
            " where dcc.disease_id=?1 and dcc.country_id=?2 order by dcc.period_end desc,dcc.id desc\n" +
            " LIMIT 0,1) e1 ,1 e,\n" +
            " (select max(dcc.death_total) from disease_country_case dcc where dcc.disease_id=?1 and dcc.country_id=?2 ) f1,\n" +
            " (select sum(temp.dt) from (\n" +
            " select max(dcc.death_total) dt from disease_country_case dcc where dcc.death_total is not null group by dcc.disease_id,dcc.country_id) temp) f  from dual",nativeQuery=true)
    public Object getDiseaseAnalysis(int diseaseId,int countryId);
}
