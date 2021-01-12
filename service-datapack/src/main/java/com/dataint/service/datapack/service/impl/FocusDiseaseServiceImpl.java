package com.dataint.service.datapack.service.impl;

import com.dataint.cloud.common.exception.DataAlreadyExistException;
import com.dataint.cloud.common.exception.DataNotExistException;
import com.dataint.service.datapack.db.dao.IFocusDiseaseDao;
import com.dataint.service.datapack.db.entity.FocusDisease;
import com.dataint.service.datapack.model.form.FocusDiseaseForm;
import com.dataint.service.datapack.model.param.FocusDiseaseParam;
import com.dataint.service.datapack.model.vo.FocusDiseaseVO;
import com.dataint.service.datapack.service.IFocusDiseaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FocusDiseaseServiceImpl implements IFocusDiseaseService {
    @Autowired
    IFocusDiseaseDao  focusDiseaseDao;
    @Override
    public Page<FocusDiseaseVO> listFocusDisease(FocusDiseaseParam focusDiseaseParam) {
        Page<FocusDisease> pagedisease = focusDiseaseDao.findAll(new Specification<FocusDisease>() {
            @Override
            public Predicate toPredicate(Root<FocusDisease> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate>  list = new ArrayList<>();
                //nameCn
                if (focusDiseaseParam.getId()!=null){
                    list.add(criteriaBuilder.equal(root.get("id").as(String.class),focusDiseaseParam.getId() ));
                }
                //showType
                if (!StringUtils.isEmpty(focusDiseaseParam.getShowType())){
                    list.add(criteriaBuilder.equal(root.get("showType").as(String.class), focusDiseaseParam.getShowType()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },focusDiseaseParam.toPageRequest());
        List<FocusDiseaseVO>  diseaseCountryCaseList =pagedisease.getContent().stream().map(FocusDiseaseVO::new).collect(Collectors.toList());


        return new PageImpl<>(diseaseCountryCaseList, pagedisease.getPageable(), pagedisease.getTotalElements());
    }

    @Override
    public FocusDisease addFocusDisease(FocusDiseaseForm focusDiseaseForm) {
        Long diseaseFormId = focusDiseaseForm.getId();
        Optional<FocusDisease> focusOpt = focusDiseaseDao.findById(diseaseFormId);
        if (!focusOpt.isPresent()) {
            throw new DataNotExistException();
        }
        FocusDisease focusDisease = focusOpt.get();

        if (!focusDisease.getShowType().equals(focusDiseaseForm.getShowType())) {
            focusDisease.setShowType(focusDiseaseForm.getShowType());
            focusDiseaseDao.save(focusDisease);
        }

        return focusDisease;
    }

    @Override
    public List<FocusDiseaseVO> listFocusDiseaseDefault() {
        List<FocusDisease> all = focusDiseaseDao.findAll();
        List<FocusDiseaseVO>    focusDiseaseVOList  =  all.stream().map(FocusDiseaseVO::new).collect(Collectors.toList());
        return   focusDiseaseVOList;
    }
}
