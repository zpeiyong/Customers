package com.dataint.monitor.service;

import com.dataint.cloud.common.model.ResultVO;

public interface IDictService {

    /**
     *
     * @return
     */
    ResultVO queryOutbreakLevels();

    /**
     *
     * @return
     */
    ResultVO queryRegions();

    /**
     *
     * @return
     */
    ResultVO queryCountries();

    /**
     *
     * @return
     */
    ResultVO queryDiseases();

    /**
     *
     * @param nameCnFirst
     * @return
     */
    ResultVO queryByNameCnFirst(String nameCnFirst);
}
