package com.dataint.service.datapack.service.impl;

import com.dataint.service.datapack.dao.ICountryDao;
import com.dataint.service.datapack.dao.IDiseasesDao;
import com.dataint.service.datapack.dao.IOutbreakLevelDao;
import com.dataint.service.datapack.dao.IRegionDao;
import com.dataint.service.datapack.dao.entity.Country;
import com.dataint.service.datapack.dao.entity.Diseases;
import com.dataint.service.datapack.dao.entity.OutbreakLevel;
import com.dataint.service.datapack.dao.entity.Region;
import com.dataint.service.datapack.model.CountryVO;
import com.dataint.service.datapack.model.DiseaseVO;
import com.dataint.service.datapack.service.IDictService;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DictServiceImpl implements IDictService {

    @Autowired
    private IOutbreakLevelDao outbreakLevelDao;

    @Autowired
    private IRegionDao regionDao;

    @Autowired
    private ICountryDao countryDao;

    @Autowired
    private IDiseasesDao diseasesDao;

    @Override
    public List<OutbreakLevel> queryOutbreakLevels() {

        return outbreakLevelDao.findAll();
    }

    @Override
    public OutbreakLevel addLevel(OutbreakLevel outbreakLevel) {
        Date now = LocalDateTime.now().toDate();
        outbreakLevel.setUpdatedBy("SYSTEM");
        outbreakLevel.setUpdatedTime(now);

        return outbreakLevelDao.save(outbreakLevel);
    }

    @Override
    public List<Region> queryRegions() {

        return regionDao.findAll();
    }

    @Override
    public List<CountryVO> queryCountries() {
        List<Country> countryList = countryDao.findAll();

        return countryList.stream().map(CountryVO::new).collect(Collectors.toList());
    }

    @Override
    public List<DiseaseVO> queryDiseases() {
        List<Diseases> diseaseList = diseasesDao.findAll();

        return diseaseList.stream().map(DiseaseVO::new).collect(Collectors.toList());
    }

    @Override
    public List<DiseaseVO> queryByNameCnFirst(String nameCnFirst) {
        List<Diseases> diseasesList = diseasesDao.findByNameCnLike(nameCnFirst + "%");

        return diseasesList.stream().map(DiseaseVO::new).collect(Collectors.toList());
    }
}
