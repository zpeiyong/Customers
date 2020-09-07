package com.dataint.service.datapack.service;

import com.dataint.service.datapack.dao.entity.OutbreakLevel;
import com.dataint.service.datapack.dao.entity.Region;
import com.dataint.service.datapack.model.CountryVO;
import com.dataint.service.datapack.model.DiseaseVO;

import java.util.List;

public interface IDictService {

    /**
     *
     * @return
     */
    List<OutbreakLevel> queryOutbreakLevels();

    /**
     *
     * @param outbreakLevel
     * @return
     */
    OutbreakLevel addLevel(OutbreakLevel outbreakLevel);

    /**
     *
     * @return
     */
    List<Region> queryRegions();

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
}
