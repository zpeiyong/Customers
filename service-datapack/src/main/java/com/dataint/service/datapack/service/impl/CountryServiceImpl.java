package com.dataint.service.datapack.service.impl;

import com.dataint.cloud.common.exception.DataAlreadyExistException;
import com.dataint.cloud.common.exception.DataNotExistException;
import com.dataint.service.datapack.dao.ICountryDao;
import com.dataint.service.datapack.dao.IRegionDao;
import com.dataint.service.datapack.dao.entity.Country;
import com.dataint.service.datapack.dao.entity.Region;
import com.dataint.service.datapack.model.CountryVO;
import com.dataint.service.datapack.model.form.CountryForm;
import com.dataint.service.datapack.model.form.CountryUpdateForm;
import com.dataint.service.datapack.model.params.CountryQueryParam;
import com.dataint.service.datapack.service.ICountryService;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements ICountryService {
    @Autowired
    private ICountryDao countryDao;

    @Autowired
    private IRegionDao regionDao;

    @Override
    public CountryVO addCountry(CountryForm countryForm) {
        // verify data
        Country ifExist = countryDao.findByNameCnOrCode(countryForm.getNameCn(), countryForm.getCode());
        if (ifExist != null) {
            throw new DataAlreadyExistException("国家名称或编码已存在!");
        }
        ifExist = countryForm.toPo(Country.class);

        // check region if exist
        Optional<Region> regionOpt = regionDao.findById(countryForm.getRegionId());
        if (!regionOpt.isPresent()) {
            throw new DataNotExistException("大洲信息不存在!");
        }
        ifExist.setRegion(regionOpt.get());

        // latitude/longitude
        if (StringUtils.isNotEmpty(countryForm.getLatitude()))
            ifExist.setLatitude(new BigDecimal(countryForm.getLatitude()));
        if (StringUtils.isNotEmpty(countryForm.getLongitude()))
            ifExist.setLongitude(new BigDecimal(countryForm.getLongitude()));

        // others
        ifExist.setCreatedTime(LocalDateTime.now().toDate());
        countryDao.save(ifExist);

        return new CountryVO(ifExist);
    }

    @Override
    public CountryVO updateCountryStatus(Integer countryId, Integer status) {
        // check if country exist
        Optional<Country> countryOpt = countryDao.findById(countryId);
        if (!countryOpt.isPresent()) {
            throw new DataNotExistException();
        }
        Country country = countryOpt.get();

        country.setStatus(status);
        country.setUpdatedTime(LocalDateTime.now().toDate());
        countryDao.save(country);

        return new CountryVO(country);
    }

    @Transactional
    @Override
    public CountryVO updateCountry(CountryUpdateForm countryUpdateForm) {
        // check if country exist
        Optional<Country> countryOpt = countryDao.findById(countryUpdateForm.getCountryId());
        if (!countryOpt.isPresent()) {
            throw new DataNotExistException();
        }
        Country country = countryOpt.get();
        // verify data
        Country ifExist = countryDao.findByNameCnOrCode(countryUpdateForm.getNameCn(), countryUpdateForm.getCode());
        if (ifExist != null && !ifExist.getId().equals(country.getId())) {
            throw new DataAlreadyExistException("国家名称或编码已存在!");
        }
        BeanUtils.copyProperties(countryUpdateForm, country);

        // latitude/longitude
        if (StringUtils.isNotEmpty(countryUpdateForm.getLatitude()))
            country.setLatitude(new BigDecimal(countryUpdateForm.getLatitude()));
        if (StringUtils.isNotEmpty(countryUpdateForm.getLongitude()))
            country.setLongitude(new BigDecimal(countryUpdateForm.getLongitude()));

        // others
        country.setUpdatedTime(LocalDateTime.now().toDate());
        countryDao.save(country);

        return new CountryVO(country);
    }

    @Override
    public boolean delCountry(Integer countryId) {
        // check if country exist
        Optional<Country> countryOpt = countryDao.findById(countryId);
        if (!countryOpt.isPresent()) {
            throw new DataNotExistException();
        }
        countryDao.deleteById(countryId);

        return true;
    }

    @Override
    public CountryVO getCountry(Integer countryId) {
        // check if country exist
        Optional<Country> countryOpt = countryDao.findById(countryId);
        if (!countryOpt.isPresent()) {
            throw new DataNotExistException();
        }

        return new CountryVO(countryOpt.get());
    }

    @Override
    public Page<CountryVO> getCountries(CountryQueryParam countryQueryParam) {
        Page<Country> countryPage = countryDao.findAll(new Specification<Country>() {
            @Override
            public Predicate toPredicate(Root<Country> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotEmpty(countryQueryParam.getKeyword())) {
                    Predicate p1 = criteriaBuilder.like(root.get("code").as(String.class), "%"+countryQueryParam.getKeyword()+"%");
                    Predicate p2 = criteriaBuilder.like(root.get("nameCn").as(String.class), "%"+countryQueryParam.getKeyword()+"%");
                    list.add(criteriaBuilder.or(p1, p2));
                }

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, countryQueryParam.toPageRequest());
        List<CountryVO> countryVOList = countryPage.getContent().stream().map(CountryVO::new).collect(Collectors.toList());

        return new PageImpl(countryVOList, countryPage.getPageable(), countryPage.getTotalElements());
    }
}
