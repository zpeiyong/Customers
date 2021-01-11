package com.dataint.service.datapack.service.impl;

import com.dataint.cloud.common.exception.DataAlreadyExistException;
import com.dataint.cloud.common.exception.DataNotExistException;
import com.dataint.service.datapack.db.dao.ICountryDao;
import com.dataint.service.datapack.db.dao.IFocusCountryDao;
import com.dataint.service.datapack.db.dao.IRegionDao;
import com.dataint.service.datapack.db.entity.Country;
import com.dataint.service.datapack.db.entity.FocusCountry;
import com.dataint.service.datapack.db.entity.Region;
import com.dataint.service.datapack.model.form.CountryForm;
import com.dataint.service.datapack.model.form.CountryUpdateForm;
import com.dataint.service.datapack.model.param.CountryQueryParam;
import com.dataint.service.datapack.model.vo.CountryVO;
import com.dataint.service.datapack.service.ICountryService;
import com.dataint.service.datapack.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
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

    @Autowired
    private IFocusCountryDao focusCountryDao;

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
        Region region = regionOpt.get();
        ifExist.setRegionId(region.getId());

        // latitude/longitude
        if (StringUtils.isNotEmpty(countryForm.getLatitude()))
            ifExist.setLatitude(new BigDecimal(countryForm.getLatitude()));
        if (StringUtils.isNotEmpty(countryForm.getLongitude()))
            ifExist.setLongitude(new BigDecimal(countryForm.getLongitude()));

        // others
        ifExist.setCreatedTime(LocalDateTime.now().toDate());
        countryDao.save(ifExist);

        CountryVO countryVO = new CountryVO(ifExist);
        countryVO.setRegion(region);

        return countryVO;
    }

    @Override
    public CountryVO updateCountryStatus(Long countryId, Integer status) {
        // check if country exist
        Optional<Country> countryOpt = countryDao.findById(countryId);
        if (!countryOpt.isPresent()) {
            throw new DataNotExistException();
        }
        Country country = countryOpt.get();

        country.setStatus(status);
        country.setUpdatedTime(LocalDateTime.now().toDate());
        countryDao.save(country);

        // region optional
        Optional<Region> regionOpt = regionDao.findById(country.getRegionId());

        CountryVO countryVO = new CountryVO(country);
        countryVO.setRegion(regionOpt.orElse(null));

        return countryVO;
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
        country = countryUpdateForm.toPo(countryUpdateForm.getCountryId(), Country.class);

        // latitude/longitude
        if (StringUtils.isNotEmpty(countryUpdateForm.getLatitude()))
            country.setLatitude(new BigDecimal(countryUpdateForm.getLatitude()));
        if (StringUtils.isNotEmpty(countryUpdateForm.getLongitude()))
            country.setLongitude(new BigDecimal(countryUpdateForm.getLongitude()));

        // others
        country.setUpdatedTime(LocalDateTime.now().toDate());
        countryDao.save(country);

        // region optional
        Optional<Region> regionOpt = regionDao.findById(country.getRegionId());

        CountryVO countryVO = new CountryVO(country);
        countryVO.setRegion(regionOpt.orElse(null));

        return countryVO;
    }

    @Override
    public boolean delCountry(Long countryId) {
        // check if country exist
        Optional<Country> countryOpt = countryDao.findById(countryId);
        if (!countryOpt.isPresent()) {
            throw new DataNotExistException();
        }
        countryDao.deleteById(countryId);

        return true;
    }

    @Override
    public CountryVO getCountry(Long countryId) {
        // check if country exist
        Optional<Country> countryOpt = countryDao.findById(countryId);
        if (!countryOpt.isPresent()) {
            throw new DataNotExistException();
        }
        Country country = countryOpt.get();

        // region optional
        Optional<Region> regionOpt = regionDao.findById(country.getRegionId());

        CountryVO countryVO = new CountryVO(country);
        countryVO.setRegion(regionOpt.orElse(null));

        return countryVO;
    }

    @Override
    public List<Country> getCountries(CountryQueryParam countryQueryParam) {
        List<Country> countryDaoAll = countryDao.findAll(new Specification<Country>() {
            @Override
            public Predicate toPredicate(Root<Country> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotEmpty(countryQueryParam.getKeyword())) {
                    Predicate p1 = criteriaBuilder.like(root.get("code").as(String.class), "%" + countryQueryParam.getKeyword() + "%");
                    Predicate p2 = criteriaBuilder.like(root.get("nameCn").as(String.class), "%" + countryQueryParam.getKeyword() + "%");
                    list.add(criteriaBuilder.or(p1, p2));
                }

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        });
        return  countryDaoAll;
    }

    @Override
    public List<Country> ListCountries() {
        List<Country> countryList = countryDao.findAll();
        return  countryList;
    }


    /**
     * 预加载所有的关注国家(直航地区)信息
     */
    @PostConstruct
    public void initDirectCountries() {
        List<Long> focusIdList = focusCountryDao.findAll().stream().map(FocusCountry::getCountryId).collect(Collectors.toList());
        List<Country> focusCountryList = countryDao.findAllByIdIn(focusIdList);

        focusCountryList.forEach(country -> {
            Constants.focusCountryMap.put(country.getId(), country);
        });
    }
}
