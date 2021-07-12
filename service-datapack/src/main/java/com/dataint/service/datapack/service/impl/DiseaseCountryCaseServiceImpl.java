package com.dataint.service.datapack.service.impl;

import com.dataint.cloud.common.dim.BaseExceptionEnum;
import com.dataint.cloud.common.exception.DataAlreadyExistException;
import com.dataint.cloud.common.exception.DataNotExistException;
import com.dataint.cloud.common.exception.DataintBaseException;
import com.dataint.cloud.common.model.Constants;
import com.dataint.cloud.common.utils.DateUtil;
import com.dataint.service.datapack.db.dao.ICountryDao;
import com.dataint.service.datapack.db.dao.IDiseaseCountryCaseDao;
import com.dataint.service.datapack.db.entity.Country;
import com.dataint.service.datapack.db.entity.DiseaseCountryCase;
import com.dataint.service.datapack.model.form.DiseaseCountryForm;
import com.dataint.service.datapack.model.param.DiseaseCountryParam;
import com.dataint.service.datapack.model.vo.CountryVO;
import com.dataint.service.datapack.model.vo.DiseaseCountryCaseVO;
import com.dataint.service.datapack.service.IDiseaseCountryCaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class DiseaseCountryCaseServiceImpl implements IDiseaseCountryCaseService {

    @Autowired
    private IDiseaseCountryCaseDao dcCaseDao;
    @Autowired
    private ICountryDao countryDao;


    @Override
    public Page<DiseaseCountryCaseVO> listDiseaseCountry(DiseaseCountryParam diseaseCountryParam) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>() {{
            add(new Sort.Order(Sort.Direction.DESC, "periodStart"));
        }};

        Page<DiseaseCountryCase> countryCases = dcCaseDao.findAll(new Specification<DiseaseCountryCase>() {
            @Override
            public Predicate toPredicate(Root<DiseaseCountryCase> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate>  list = new ArrayList<>();
                //diseaseNameCn
                if (diseaseCountryParam.getDiseaseId()!=null){
                    list.add(criteriaBuilder.equal(root.get("diseaseId").as(Long.class), diseaseCountryParam.getDiseaseId()));
                }
                //countryNameCn
                if (!StringUtils.isEmpty(diseaseCountryParam.getCountryNameCn())){
                    list.add(criteriaBuilder.like(root.get("countryNameCn").as(String.class), "%"+diseaseCountryParam.getCountryNameCn()+"%"));
                }
                //showType
                if (!StringUtils.isEmpty(diseaseCountryParam.getShowType())){
                    list.add(criteriaBuilder.equal(root.get("showType").as(String.class), diseaseCountryParam.getShowType()));
                }
                //periodStart
                if (diseaseCountryParam.getPeriodStart()!=null){
                    list.add(criteriaBuilder.equal(root.get("periodStart").as(String.class), diseaseCountryParam.getPeriodStart()));
                }
                if (diseaseCountryParam.getId()!=null){
                    list.add(criteriaBuilder.equal(root.get("id").as(Long.class), diseaseCountryParam.getId()));
                }

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },diseaseCountryParam.toPageRequest(Sort.by(orders)));

        List<DiseaseCountryCaseVO>  diseaseCountryCaseList =countryCases.getContent().stream().map(DiseaseCountryCaseVO::new).collect(Collectors.toList());

        return new PageImpl<>(diseaseCountryCaseList, countryCases.getPageable(), countryCases.getTotalElements());
    }

    @Override
    public DiseaseCountryCase addDiseaseCountryCase(DiseaseCountryForm dcForm) {
        Long id = dcForm.getId();
        DiseaseCountryCase diseaseCountryCase;
        if (id == null){
            diseaseCountryCase = dcForm.toPo(DiseaseCountryCase.class);
            Long diseaseId = dcForm.getDiseaseId();
            Long countryId = dcForm.getCountryId();
            Date periodStart = dcForm.getPeriodStart();

            List<DiseaseCountryCase> caseList = dcCaseDao.findByDiseaseIdAndCountryIdAndPeriodStart(diseaseId, countryId, periodStart);
            if (caseList.size() > 0){
                throw new DataAlreadyExistException("国家名称或者病例数据在此时间段内已经存在");
            }

            // 换算周期截止时间
            String periodStartStr = Constants.DateTimeSDF.format(periodStart);
            String periodEndStr;
            try {
                if ("daily".equals(diseaseCountryCase.getShowType())) {
                    periodEndStr = DateUtil.getDayEnd(periodStartStr);
                } else if ("weekly".equals(diseaseCountryCase.getShowType())) {
                    periodEndStr = DateUtil.getWeekEnd(periodStartStr);
                } else if ("monthly".equals(diseaseCountryCase.getShowType())) {
                    periodEndStr = DateUtil.getEndTimeOfMonth(periodStartStr);
                } else if ("quarterly".equals(diseaseCountryCase.getShowType())) {
                    periodEndStr = DateUtil.getEndTimeOfQuarter(periodStartStr);
                } else {
                    periodEndStr = DateUtil.getEndTimeOfYearly(periodStartStr);
                }
                diseaseCountryCase.setPeriodEnd(Constants.getDateTimeFormat().parse(periodEndStr));
            } catch (ParseException e) {
                throw new DataintBaseException(BaseExceptionEnum.DATE_PARSE_ERROR);
            }
            dcCaseDao.save(diseaseCountryCase);

            return diseaseCountryCase;
        }
        //修改
        else {
            Optional<DiseaseCountryCase> caseDaoById = dcCaseDao.findById(id);
            if (!caseDaoById.isPresent()){
                throw new DataNotExistException();
            }
            diseaseCountryCase = caseDaoById.get();

            // 前台接受的值进行比较直接覆盖原来的
            diseaseCountryCase.setConfirmAdd(dcForm.getConfirmAdd());
            diseaseCountryCase.setCureAdd(dcForm.getCureAdd());
            diseaseCountryCase.setDeathAdd(dcForm.getDeathAdd());
            diseaseCountryCase.setCureTotal(dcForm.getCureTotal());
            diseaseCountryCase.setDeathTotal(dcForm.getDeathTotal());
            diseaseCountryCase.setConfirmTotal(dcForm.getConfirmTotal());

        }
        dcCaseDao.save(diseaseCountryCase);

        return diseaseCountryCase;
    }

    @Override
    public List<CountryVO> getCountriesByParam(Long diseaseId, String showType, String periodStart) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(periodStart);
        } catch (ParseException e) {
            throw new DataintBaseException(BaseExceptionEnum.DATE_PARSE_ERROR);
        }
        List<DiseaseCountryCase> countriesByParamList = dcCaseDao.findByDiseaseIdAndShowTypeAndPeriodStart(diseaseId, showType, date);
        List<Country> countryList = countryDao.findAll();
        Map<Long, Country> countryMap = new HashMap<>();
        countryList.forEach(country -> {
            countryMap.put(country.getId(), country);
        });
        countriesByParamList.forEach(dcCase -> {
            countryMap.remove(dcCase.getCountryId());
        });
        List<CountryVO> remainCountryList = countryMap.values().stream().map(CountryVO::new).collect(toList());

        return remainCountryList;
    }

    @Override
    public DiseaseCountryCaseVO getLatestCasesByParam(Long diseaseId, Long countryId) {
        DiseaseCountryCase latestDCCase = dcCaseDao.findTopByDiseaseIdAndCountryIdOrderByStatisticDateDesc(diseaseId, countryId);
        if (latestDCCase == null) {
            latestDCCase = new DiseaseCountryCase();
        }

        return new DiseaseCountryCaseVO(latestDCCase);
    }

    @Override
    public List<Map<String,Object>> getCountryDataTj() {
        List<Map<String, Object>> countryDataTj = dcCaseDao.getCountryDataTj();
        return countryDataTj;
    }

    @Override
    public List<Map<String, Object>> getDiseaseDataTj() {
        List<Map<String, Object>> diseaseDataTj = dcCaseDao.getDiseaseDataTj();
        return diseaseDataTj;
    }



    @Override
    public List<DiseaseCountryCaseVO> getForCountryRisk1(int diseaseId, int countryId, int week) {

        List<Map<String,String>> daoResult = this.dcCaseDao.getForCountryRisk1(diseaseId,countryId,week,0);

        List<Map<String,String>> lastDaoResult = this.dcCaseDao.getForCountryRisk1(diseaseId,countryId,week - 1,1);

        List<DiseaseCountryCaseVO> result = new ArrayList<DiseaseCountryCaseVO>();

        Map<String,String> lastMap = null;

        int index = 0;

        for(Map<String,String> daoMap : daoResult) {

            if(lastMap == null) {

                lastMap = daoMap;
                continue;
            }

            Map<String,String> lastYearMap = lastDaoResult.get(index ++);

            DiseaseCountryCaseVO vo = new DiseaseCountryCaseVO();

            vo.setConfirmAdd(Integer.parseInt(daoMap.get("confirm_add")));
            vo.setDeathAdd(Integer.parseInt(daoMap.get("death_add")));
            vo.setWeek(Integer.parseInt(daoMap.get("week_num")));

            float lastConfirmAdd = Float.parseFloat(lastMap.get("confirm_add"));
            float lastDeathAdd = Float.parseFloat(lastMap.get("death_add"));

            float lastYearConfirmAdd = Float.parseFloat(lastYearMap.get("confirm_add"));
            float lastYearDeathAdd = Float.parseFloat(lastYearMap.get("death_add"));
            lastMap = daoMap;

            float confirmChain = (vo.getConfirmAdd() - lastConfirmAdd) / lastConfirmAdd;
            float deathChain =  (vo.getDeathAdd() - lastDeathAdd) / lastDeathAdd;

            float confirmLastYearChain = (vo.getConfirmAdd() - lastYearConfirmAdd) / lastYearConfirmAdd;
            float deathLastYearChain = (vo.getDeathAdd() - lastYearDeathAdd) / lastYearDeathAdd;

            vo.setChainComparison(confirmChain);
            vo.setChainDeathComparison(deathChain);

            vo.setYearComparison(confirmLastYearChain);
            vo.setYearDeathComparison(deathLastYearChain);


            result.add(vo);


        }
        return result;

    }

    @Override
    public List<DiseaseCountryCaseVO> getForCountryPreDay(int diseaseId, int countryId, int day) {

        Pageable page = PageRequest.of(1,day);

        List<DiseaseCountryCase> cases = this.dcCaseDao.getForCountryDiseaseAdd(Integer.valueOf(diseaseId).longValue() ,countryId,page);

        List<DiseaseCountryCaseVO> result = new ArrayList<DiseaseCountryCaseVO>();

        for(DiseaseCountryCase c : cases) {
            DiseaseCountryCaseVO vo = new DiseaseCountryCaseVO();

            vo.setPeriodEnd(c.getPeriodEnd());
            vo.setConfirmAdd(c.getConfirmAdd());

            result.add(vo);
        }

        return result;
    }

    public Object getDiseaseAnalysis(int diseaseId,int countryId) {

        return this.dcCaseDao.getDiseaseAnalysis(diseaseId,countryId);
    }

}
