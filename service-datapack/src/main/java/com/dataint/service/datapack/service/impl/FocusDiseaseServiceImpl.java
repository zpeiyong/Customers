package com.dataint.service.datapack.service.impl;

import com.dataint.cloud.common.exception.DataAlreadyExistException;
import com.dataint.service.datapack.db.dao.IFocusDiseaseDao;
import com.dataint.service.datapack.db.entity.FocusDisease;
import com.dataint.service.datapack.model.param.FocusDiseaseParam;
import com.dataint.service.datapack.service.IFocusDiseaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class FocusDiseaseServiceImpl implements IFocusDiseaseService {
    @Autowired
    IFocusDiseaseDao  focusDiseaseDao;
    @Override
    public List<FocusDisease> listFocusDisease(FocusDiseaseParam focusDiseaseParam) {
        List<FocusDisease> diseaseList = focusDiseaseDao.findAll(new Specification<FocusDisease>() {
            @Override
            public Predicate toPredicate(Root<FocusDisease> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate>  list = new ArrayList<>();
                //nameCn
                if (!StringUtils.isEmpty(focusDiseaseParam.getNameCn())){
                    list.add(criteriaBuilder.equal(root.get("nameCn").as(String.class),focusDiseaseParam.getNameCn() ));
                }
                //showType
                if (!StringUtils.isEmpty(focusDiseaseParam.getShowType())){
                    list.add(criteriaBuilder.equal(root.get("showType").as(String.class), focusDiseaseParam.getShowType()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        });
        return diseaseList;
    }

    @Override
    public FocusDisease addFocusDisease(FocusDisease focusDisease) {
        String nameCn = focusDisease.getNameCn();
        String showType = focusDisease.getShowType();
        List<FocusDisease> focusDiseaseList = focusDiseaseDao.findByNameCnAndShowType(nameCn, showType);
        if (focusDiseaseList.size()>0){
            throw  new DataAlreadyExistException("病例已经存在");
        }
        else {
            FocusDisease disease = focusDiseaseDao.save(focusDisease);
            return disease;
        }
    }

    @Override
    public List<FocusDisease> listFocusDiseaseDefault() {
        List<FocusDisease> all = focusDiseaseDao.findAll();
        return all;
    }
}
