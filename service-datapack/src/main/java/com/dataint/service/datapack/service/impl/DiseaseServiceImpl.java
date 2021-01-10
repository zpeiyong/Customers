package com.dataint.service.datapack.service.impl;

import com.alibaba.fastjson.JSON;
import com.dataint.cloud.common.exception.DataAlreadyExistException;
import com.dataint.cloud.common.exception.DataNotExistException;
import com.dataint.cloud.common.exception.DataintBaseException;
import com.dataint.cloud.common.model.Constants;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.cloud.common.utils.DateUtil;
import com.dataint.service.datapack.db.dao.IDiseaseCountryCaseDao;
import com.dataint.service.datapack.db.dao.IDiseasesDao;
import com.dataint.service.datapack.db.dao.IFocusDiseaseDao;
import com.dataint.service.datapack.db.entity.Diseases;
import com.dataint.service.datapack.db.entity.FocusDisease;
import com.dataint.service.datapack.model.form.DiseaseForm;
import com.dataint.service.datapack.model.form.DiseaseUpdateForm;
import com.dataint.service.datapack.model.param.DiseaseQueryParam;
import com.dataint.service.datapack.model.vo.DiseaseCountryCaseVO;
import com.dataint.service.datapack.model.vo.DiseaseVO;
import com.dataint.service.datapack.model.vo.FocusDiseaseVO;
import com.dataint.service.datapack.service.IDiseaseService;
import com.dataint.service.datapack.utils.FirstLetterUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DiseaseServiceImpl implements IDiseaseService {
    @Autowired
    private IDiseasesDao diseasesDao;
    @Autowired
    private IFocusDiseaseDao focusDiseaseDao;
    @Autowired
    private IDiseaseCountryCaseDao diseaseCountryCaseDao;

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
    public DiseaseVO updateDiseaseStatus(Long diseaseId, Integer status) {
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
    public boolean delDisease(Long diseaseId) {
        // check if diseases exist
        Optional<Diseases> diseasesOpt = diseasesDao.findById(diseaseId);
        if (!diseasesOpt.isPresent()) {
            throw new DataNotExistException();
        }
        diseasesDao.deleteById(diseaseId);

        return true;
    }

    @Override
    public DiseaseVO getDisease(Long diseaseId) {
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

    @Override
    public Page<FocusDiseaseVO> queryFocusDiseases(DiseaseQueryParam diseaseQueryParam) {
        // 热门传染病在前且中文名正序
        List<Sort.Order> orders = new ArrayList<Sort.Order>() {{
            add(new Sort.Order(Sort.Direction.DESC, "ifPopular"));
            add(new Sort.Order(Sort.Direction.ASC, "nameCnFirst"));
        }};

        Page<FocusDisease> focusPage = focusDiseaseDao.findAll(new Specification<FocusDisease>() {
            @Override
            public Predicate toPredicate(Root<FocusDisease> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                // 关键词
                if (StringUtils.isNotEmpty(diseaseQueryParam.getKeyword())) {
                    Predicate p2 = criteriaBuilder.like(root.get("nameCn").as(String.class), "%"+diseaseQueryParam.getKeyword()+"%");
                    Predicate p3 = criteriaBuilder.like(root.get("nameEn").as(String.class), "%"+diseaseQueryParam.getKeyword()+"%");
                    list.add(criteriaBuilder.or(p2, p3));
                }

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, diseaseQueryParam.toPageRequest(Sort.by(orders)));

        List<FocusDiseaseVO> focusVOList = focusPage.getContent().stream().map(FocusDiseaseVO::new).collect(Collectors.toList());
        return new PageImpl(focusVOList, focusPage.getPageable(), focusPage.getTotalElements());
    }

    @Override
    public List<DiseaseCountryCaseVO> getCasesByCountryId(Long countryId, String dateStr, PageParam pageParam) {
        // 用于过滤传染病是否展示在前端
        String dateTimeStr = DateUtil.getCurrentTime();
        if (!StringUtils.isEmpty(dateStr)) {
            dateTimeStr = dateStr + " 00:00:00";
        }
        String dailyStartStr = DateUtil.getCurrentTime();;
        String weeklyStartStr = DateUtil.getLastWeekStart();
        String quarterlyStartStr = DateUtil.getStartTimeOfQuarter();
        try {
            dailyStartStr = DateUtil.getYesterdayStart(dateTimeStr);
            weeklyStartStr = DateUtil.getWeekStart(dateTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        List<Map<String,Object>> latestCaseList = diseaseCountryCaseDao.getLatestDiseaseCasesByCountry(countryId);
        String finalDailyStartStr = dailyStartStr;
        String finalWeeklyStartStr = weeklyStartStr;
        List<DiseaseCountryCaseVO> latestCaseVOList = latestCaseList.stream()
                .map((e) -> JSON.parseObject(JSON.toJSONString(e), DiseaseCountryCaseVO.class))
                // 根据不同传染病的不同统计周期, 过滤前一周期内没有数据更新的数据
                .filter((e) -> {
                    if (!StringUtils.isEmpty(e.getShowType())) {
                        String showType = e.getShowType();
                        String startTimeStr;
                        if ("daily".equals(showType)) {
                            startTimeStr = finalDailyStartStr;
                        } else if ("weekly".equals(showType)) {
                            startTimeStr = finalWeeklyStartStr;
                        } else if ("quarterly".equals(showType)) {
                            startTimeStr = quarterlyStartStr;
                        } else {
                            return false;
                        }

                        // 与统计起始时间比较
                        return startTimeStr.equals(Constants.getDateTimeFormat().format(e.getPeriodStart()));
                    } else
                        return false;
                })
                .collect(Collectors.toList());

        int rstSize = latestCaseVOList.size();
        if (rstSize <= pageParam.getPageSize())
            return latestCaseVOList;
        else
            return latestCaseVOList.subList(0, pageParam.getPageSize()-1);
    }

    @Override
    public DiseaseCountryCaseVO getCasesByCidAndDid(Long countryId, Long diseaseId, String dateStr) {
        // 计算前一天的统计时间
        String dateTimeStr = DateUtil.getCurrentTime();
        if (!StringUtils.isEmpty(dateStr)) {
            dateTimeStr = dateStr + " 00:00:00";
        }
        Date yesStartDate;
        try {
            yesStartDate = Constants.getDateFormat().parse(DateUtil.getYesterdayStart(dateTimeStr));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new DataintBaseException("日期参数有误!", 300);
        }

        DiseaseCountryCaseVO diseaseCountryCaseVO = diseaseCountryCaseDao.getCasesByCountryIdAndDiseaseId(yesStartDate, countryId, diseaseId);

        return diseaseCountryCaseVO;
    }
}
