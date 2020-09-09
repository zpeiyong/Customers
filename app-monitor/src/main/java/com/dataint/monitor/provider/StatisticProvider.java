package com.dataint.monitor.provider;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.ResultVO;
import org.springframework.stereotype.Component;


@Component
public interface StatisticProvider {
    /* Statistic */

    ResultVO<JSONObject> getPeriodBasic( String startTime, String endTime);


    ResultVO<Integer> getArticleTotal();


    ResultVO<JSONObject> getMapInfoByCountry(String startTime, String endTime, Integer countryId);
}
