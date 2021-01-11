package com.dataint.monitor.adapt.impl;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.adapt.ICountryAdapt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CountryAdaptImpl implements ICountryAdapt {
    @Value("${service.datapack.baseUrl}")
    private String baseUrl;
    @Override
    public ResultVO getCountries(String keyword) {
        JSONObject jsonObject = GetPostUtil.sendGet("http://" +baseUrl + "/country/all");
        return ResultVO.success(jsonObject);
    }
}
