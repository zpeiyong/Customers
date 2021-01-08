package com.dataint.service.datapack.service.impl;

import com.dataint.cloud.common.exception.DataAlreadyExistException;
import com.dataint.service.datapack.db.dao.IDiseaseCountryCaseDao;
import com.dataint.service.datapack.db.entity.DiseaseCountryCase;
import com.dataint.service.datapack.model.param.DiseaseCountryParam;
import com.dataint.service.datapack.service.IDiseaseCountryCaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DiseaseCountryCaseServiceImpl implements IDiseaseCountryCaseService {

    @Autowired
    private IDiseaseCountryCaseDao caseDao;
    @Override
    public List<DiseaseCountryCase> listDiseaseCountry(DiseaseCountryParam diseaseCountryParam) {

        List<DiseaseCountryCase> countryCases = caseDao.findAll(new Specification<DiseaseCountryCase>() {
            @Override
            public Predicate toPredicate(Root<DiseaseCountryCase> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate>  list = new ArrayList<>();
                //diseaseNameCn
                if (!StringUtils.isEmpty(diseaseCountryParam.getDiseaseNameCn())){
                    list.add(criteriaBuilder.equal(root.get("diseaseNameCn").as(String.class), diseaseCountryParam.getDiseaseNameCn()));
                }
                //countryNameCn
                if (!StringUtils.isEmpty(diseaseCountryParam.getCountryNameCn())){
                    list.add(criteriaBuilder.equal(root.get("countryNameCn").as(String.class), diseaseCountryParam.getCountryNameCn()));
                }
                //showType
                if (!StringUtils.isEmpty(diseaseCountryParam.getShowType())){
                    list.add(criteriaBuilder.equal(root.get("showType").as(String.class), diseaseCountryParam.getShowType()));
                }
                //periodStart
                if (diseaseCountryParam.getPeriodStart()!=null){
                    list.add(criteriaBuilder.equal(root.get("periodStart").as(String.class), diseaseCountryParam.getPeriodStart()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        });
        return  countryCases;
    }

    @Override
    public DiseaseCountryCase addDieaseCountry(DiseaseCountryCase countryCase) {
        String diseaseCn = countryCase.getDiseaseNameCn();
        String countryCn = countryCase.getCountryNameCn();
        Date start = countryCase.getPeriodStart();
        List<DiseaseCountryCase> caseList = caseDao.findByDiseaseNameCnAndCountryNameCnAndPeriodStart(diseaseCn, countryCn, start);
        if (caseList .size()>0){
            throw  new DataAlreadyExistException("国家名称或者病例数据在此时间段内已经存在");
        }
        else{
            DiseaseCountryCase save = caseDao.save(countryCase);
        return save;
        }
    }
}
