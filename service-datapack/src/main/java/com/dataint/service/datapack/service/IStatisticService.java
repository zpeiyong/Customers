package com.dataint.service.datapack.service;

import com.alibaba.fastjson.JSONObject;

public interface IStatisticService {

    /**
     *
     * @param startTime
     * @param endTime
     * @return
     */
    JSONObject getPeriodBasic(String startTime, String endTime);

    /**
     *
     * @return
     */
    long getArticleTotal();

    /**
     *
     * @param startTime
     * @param endTime
     * @param countryId
     * @return
     */
    JSONObject getMapInfoByCountry(String startTime, String endTime, Integer countryId);
}
