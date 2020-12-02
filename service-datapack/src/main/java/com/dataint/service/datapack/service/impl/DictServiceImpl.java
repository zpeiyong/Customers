package com.dataint.service.datapack.service.impl;

import com.dataint.service.datapack.db.dao.ICountryDao;
import com.dataint.service.datapack.db.dao.IDiseasesDao;
import com.dataint.service.datapack.db.dao.IMediaTypeDao;
import com.dataint.service.datapack.db.dao.IRegionDao;
import com.dataint.service.datapack.db.entity.Country;
import com.dataint.service.datapack.db.entity.Diseases;
import com.dataint.service.datapack.db.entity.MediaType;
import com.dataint.service.datapack.db.entity.Region;
import com.dataint.service.datapack.model.vo.CountryVO;
import com.dataint.service.datapack.model.vo.DiseaseVO;
import com.dataint.service.datapack.model.vo.RegionVO;
import com.dataint.service.datapack.service.IDictService;
import com.dataint.service.datapack.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DictServiceImpl implements IDictService {
//
//    @Autowired
//    private IOutbreakLevelDao outbreakLevelDao;

    @Autowired
    private IRegionDao regionDao;

    @Autowired
    private ICountryDao countryDao;

    @Autowired
    private IDiseasesDao diseasesDao;

    @Autowired
    private IMediaTypeDao mediaTypeDao;

//    @Override
//    public List<OutbreakLevel> queryOutbreakLevels() {
//
//        return outbreakLevelDao.findAll();
//    }
//
//    @Override
//    public OutbreakLevel addLevel(OutbreakLevel outbreakLevel) {
//        Date now = LocalDateTime.now().toDate();
//        outbreakLevel.setUpdatedBy("SYSTEM");
//        outbreakLevel.setUpdatedTime(now);
//
//        return outbreakLevelDao.save(outbreakLevel);
//    }

    @Override
    public List<RegionVO> queryRegions() {
        List<Region> regionList = regionDao.findAllByOrderByNameCn();
        final int[] sort = {2};
        List<RegionVO> regionVOList = regionList.stream()
                .map(region -> {
                    RegionVO regionVO = new RegionVO(region);
                    // 将"其他"放在最后
                    if ("其他".equals(region.getNameCn())) {
                        regionVO.setSort(99);
                    } else {
                        regionVO.setSort(sort[0]);
                        sort[0] += 1;
                    }
                    return regionVO;
                }).collect(Collectors.toList());

        // 直航地区
        RegionVO directRegion = new RegionVO();
        directRegion.setId(0L);
        directRegion.setNameCn("直航地区");
        directRegion.setCode("direct");
        directRegion.setSort(1);
        regionVOList.add(directRegion);

        return regionVOList;
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

    @Override
    public List<MediaType> queryMediaTypes() {

        return mediaTypeDao.findAll();
    }

    @Override
    public List<Map<String, Object>> queryArticleTypes() {
        List<Map<String, Object>> articleTypeList = new ArrayList<>();

        final int[] sort = {1};
        Constants.articleTypeMap.forEach((key, value) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("code", key);
            map.put("name", value);
            if ("other".equals(key)) {
                map.put("sort", 99);
            } else {
                map.put("sort", sort[0]);
                sort[0] += 1;
            }

            articleTypeList.add(map);
        });

        return articleTypeList;
    }
}
