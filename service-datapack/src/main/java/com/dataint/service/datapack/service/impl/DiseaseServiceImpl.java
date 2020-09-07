package com.dataint.service.datapack.service.impl;

import com.dataint.cloud.common.exception.DataAlreadyExistException;
import com.dataint.cloud.common.exception.DataNotExistException;
import com.dataint.service.datapack.dao.IDiseasesDao;
import com.dataint.service.datapack.dao.entity.Diseases;
import com.dataint.service.datapack.model.DiseaseVO;
import com.dataint.service.datapack.model.form.DiseaseForm;
import com.dataint.service.datapack.model.form.DiseaseUpdateForm;
import com.dataint.service.datapack.model.params.DiseaseQueryParam;
import com.dataint.service.datapack.service.IDiseaseService;
import com.dataint.service.datapack.utils.FirstLetterUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiseaseServiceImpl implements IDiseaseService {
    @Autowired
    private IDiseasesDao diseasesDao;

    @Override
    public DiseaseVO addDisease(DiseaseForm diseaseForm) {
        // verify data
        Diseases ifExist = diseasesDao.findByNameCnOrNameEn(diseaseForm.getNameCn(), diseaseForm.getNameEn());
        if (ifExist != null) {
            throw new DataAlreadyExistException("传染病中文名称或英文名称已存在!");
        }
        ifExist = diseaseForm.toPo(Diseases.class);

        // first letter of nameCn
        if (StringUtils.isNotEmpty(ifExist.getNameCn())) {
            // 获取diseaseCn的拼音首字母
            String firstLetter = FirstLetterUtil.getFirstLetter(ifExist.getNameCn());
            ifExist.setNameCnFirst(firstLetter.toUpperCase());
        }

        // others
        ifExist.setCreatedTime(LocalDateTime.now().toDate());
        diseasesDao.save(ifExist);

        return new DiseaseVO(ifExist);
    }

    @Transactional
    @Override
    public DiseaseVO updateDiseaseStatus(Integer diseaseId, Integer status) {
        // check if diseases exist
        Optional<Diseases> diseasesOpt = diseasesDao.findById(diseaseId);
        if (!diseasesOpt.isPresent()) {
            throw new DataNotExistException();
        }
        Diseases diseases = diseasesOpt.get();

        diseases.setStatus(status);
        diseases.setUpdatedTime(LocalDateTime.now().toDate());
        diseasesDao.save(diseases);

        return new DiseaseVO(diseases);
    }

    @Transactional
    @Override
    public DiseaseVO updateDisease(DiseaseUpdateForm diseaseUpdateForm) {
        // check if diseases exist
        Optional<Diseases> diseasesOpt = diseasesDao.findById(diseaseUpdateForm.getDiseaseId());
        if (!diseasesOpt.isPresent()) {
            throw new DataNotExistException();
        }
        Diseases diseases = diseasesOpt.get();
        // verify data
        Diseases ifExist = diseasesDao.findByNameCnOrNameEn(diseaseUpdateForm.getNameCn(), diseaseUpdateForm.getNameEn());
        if (ifExist != null && !ifExist.getId().equals(diseases.getId())) {
            throw new DataAlreadyExistException("传染病中文名称或英文名称已存在!");
        }
        BeanUtils.copyProperties(diseaseUpdateForm, diseases);

        // first letter of nameCn
        if (StringUtils.isNotEmpty(diseases.getNameCn())) {
            // 获取diseaseCn的拼音首字母
            String firstLetter = FirstLetterUtil.getFirstLetter(diseases.getNameCn());
            diseases.setNameCnFirst(firstLetter.toUpperCase());
        }

        // others
        diseases.setUpdatedTime(LocalDateTime.now().toDate());
        diseasesDao.save(diseases);

        return new DiseaseVO(diseases);
    }

    @Override
    public boolean delDisease(Integer diseaseId) {
        // check if diseases exist
        Optional<Diseases> diseasesOpt = diseasesDao.findById(diseaseId);
        if (!diseasesOpt.isPresent()) {
            throw new DataNotExistException();
        }
        diseasesDao.deleteById(diseaseId);

        return true;
    }

    @Override
    public DiseaseVO getDisease(Integer diseaseId) {
        // check if diseases exist
        Optional<Diseases> diseasesOpt = diseasesDao.findById(diseaseId);
        if (!diseasesOpt.isPresent()) {
            throw new DataNotExistException();
        }

        return new DiseaseVO(diseasesOpt.get());
    }

    @Override
    public Page<DiseaseVO> getDiseases(DiseaseQueryParam diseaseQueryParam) {
        Page<Diseases> diseasePage = diseasesDao.findAll(new Specification<Diseases>() {
            @Override
            public Predicate toPredicate(Root<Diseases> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotEmpty(diseaseQueryParam.getKeyword())) {
                    Predicate p1 = criteriaBuilder.like(root.get("icd10Code").as(String.class), "%"+diseaseQueryParam.getKeyword()+"%");
                    Predicate p2 = criteriaBuilder.like(root.get("nameCn").as(String.class), "%"+diseaseQueryParam.getKeyword()+"%");
                    Predicate p3 = criteriaBuilder.like(root.get("nameEn").as(String.class), "%"+diseaseQueryParam.getKeyword()+"%");
                    list.add(criteriaBuilder.or(p1, p2, p3));
                }

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, diseaseQueryParam.toPageRequest());
        List<DiseaseVO> diseaseVOList = diseasePage.getContent().stream().map(DiseaseVO::new).collect(Collectors.toList());

        return new PageImpl(diseaseVOList, diseasePage.getPageable(), diseasePage.getTotalElements());
    }
}
