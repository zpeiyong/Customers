package com.dataint.monitor.provider.fallback;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.provider.StatisticProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StatisticProviderFallback implements StatisticProvider {
    @Value("${service.datapack.baseUrl}")
    private String baseUrl;


    @Override
    public ResultVO<JSONObject> getPeriodBasic(String startTime, String endTime) {
        JSONObject jsonObject = GetPostUtil.sendGet(baseUrl + "/statistic/getPeriodBasic");
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO<Integer> getArticleTotal() {
        JSONObject jsonObject = GetPostUtil.sendGet(baseUrl + "/statistic/getArticleTotal");
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO<JSONObject> getMapInfoByCountry(String startTime, String endTime, Integer countryId) {
        JSONObject jsonObject = GetPostUtil.sendGet(baseUrl + "/statistic/getMapInfoByCountry");
        return ResultVO.success(jsonObject);
    }
}
