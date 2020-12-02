package com.dataint.service.datapack.service;

import com.dataint.service.datapack.db.entity.MediaType;
import com.dataint.service.datapack.model.vo.CountryVO;
import com.dataint.service.datapack.model.vo.DiseaseVO;
import com.dataint.service.datapack.model.vo.RegionVO;

import java.util.List;
import java.util.Map;

public interface IDictService {

//    /**
//     *
//     * @return
//     */
//    List<OutbreakLevel> queryOutbreakLevels();
//
//    /**
//     *
//     * @param outbreakLevel
//     * @return
//     */
//    OutbreakLevel addLevel(OutbreakLevel outbreakLevel);

    /**
     *
     * @return
     */
    List<RegionVO> queryRegions();

    /**
     *
     * @return
     */
    List<CountryVO> queryCountries();

    /**
     *
     * @return
     */
    List<DiseaseVO> queryDiseases();

    /**
     *
     * @param nameCnFirst
     * @return
     */
    List<DiseaseVO> queryByNameCnFirst(String nameCnFirst);

    /**
     *
     * @return
     */
    List<MediaType> queryMediaTypes();

    /**
     *
     * @return
     */
    List<Map<String, Object>> queryArticleTypes();
}
