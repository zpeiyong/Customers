package com.dataint.service.datapack.service.impl;

import com.dataint.cloud.common.exception.DataAlreadyExistException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
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
    private IDiseaseCountryCaseDao caseDao;
    @Autowired
    private  ICountryDao countryDao;


    @Override
    public Page<DiseaseCountryCaseVO> listDiseaseCountry(DiseaseCountryParam diseaseCountryParam) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>() {{
            add(new Sort.Order(Sort.Direction.DESC, "periodStart"));
        }};

        Page<DiseaseCountryCase> countryCases = caseDao.findAll(new Specification<DiseaseCountryCase>() {
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
        return  new PageImpl<>(diseaseCountryCaseList, countryCases.getPageable(), countryCases.getTotalElements());
    }

    @Override
    public DiseaseCountryCase addDieaseCountry(DiseaseCountryForm countryCase) {
        Long id = countryCase.getId();
        if (id==null){
            DiseaseCountryCase diseaseCountryCase = countryCase.toPo(DiseaseCountryCase.class);
            Long diseaseId = countryCase.getDiseaseId();
            Long countryId = countryCase.getCountryId();
            Date start = countryCase.getPeriodStart();

            List<DiseaseCountryCase> caseList = caseDao.findByDiseaseIdAndCountryIdAndPeriodStart(diseaseId, countryId, start);
            if (caseList .size()>0 &&id ==null){
                throw  new DataAlreadyExistException("国家名称或者病例数据在此时间段内已经存在");
            }
            else{
                DiseaseCountryCase CountryCaseSave = caseDao.save(diseaseCountryCase);
                return CountryCaseSave;
            }
        }
        //修改
        else {
            Optional<DiseaseCountryCase> caseDaoById = caseDao.findById(id);
            if (!caseDaoById.isPresent()){

            }
            DiseaseCountryCase diseaseCountryCase = caseDaoById.get();

                //数据库传过来的值和 前台接受的值进行比较
            if (diseaseCountryCase.getConfirmAdd()!=countryCase.getConfirmAdd()){
                diseaseCountryCase.setConfirmAdd(countryCase.getConfirmAdd());
            }
            if (diseaseCountryCase.getCureAdd()!=countryCase.getCureAdd()){
                diseaseCountryCase.setCureAdd(countryCase.getCureAdd());
            }
            if (diseaseCountryCase.getDeathAdd()!=countryCase.getDeathAdd()){
                diseaseCountryCase.setDeathAdd(countryCase.getDeathAdd());
            }
            if (diseaseCountryCase.getCureTotal()!=countryCase.getCureTotal()){
                diseaseCountryCase.setCureTotal(countryCase.getCureTotal());
            }
            if (diseaseCountryCase.getDeathTotal()!=countryCase.getDeathTotal()){
                diseaseCountryCase.setDeathTotal(countryCase.getDeathTotal());
            }
            if (diseaseCountryCase.getConfirmTotal()!=countryCase.getConfirmTotal()){
                diseaseCountryCase.setConfirmTotal(countryCase.getConfirmTotal());
            }
            DiseaseCountryCase CountryCaseSave = caseDao.save(diseaseCountryCase);
            return CountryCaseSave;

        }
    }


    @Override
    public List<CountryVO> getCountriesByParam(Long diseaseId, String showType, String periodStart) throws  ParseException  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(periodStart);
        List<DiseaseCountryCase> countriesByParamList = caseDao.findByDiseaseIdAndShowTypeAndPeriodStart(diseaseId, showType, date);
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
}
