package com.dataint.monitor.adapt.impl;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.adapt.IDiseaseCountryCaseAdapt;
import com.dataint.monitor.model.DiseaseCountryCase;
import com.dataint.monitor.model.param.DiseaseCountryParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class DiseaseCountryCaseAdaptImpl implements IDiseaseCountryCaseAdapt {
    @Value("${service.datapack.baseUrl}")
    private String baseUrl;
    @Override
    public Object listDiseaseCountry(DiseaseCountryParam diseaseCountryParam) {

        String url ="http://" +  baseUrl + "/countryCase/diseaseCountryCaseList";
        JSONObject jsonObject = GetPostUtil.sendGet(url);
        return ResultVO.success(jsonObject);
    }

    @Override
    public Object addDieaseCountry(DiseaseCountryCase countryCase) {
        String url ="http://" +  baseUrl + "/countryCase/addDiseaseCountry";
        String jsonString = JSONObject.toJSONString(countryCase);
        return GetPostUtil.sendPost(url, jsonString);
    }

    @Override
    public Object getCountriesByParam(Long diseaseId, String showType, String periodStart) {
        HashMap<String, String> map = new HashMap<>();
        if (diseaseId!=null){
            map.put("diseaseId", diseaseId.toString());
        }
        if (showType!=null){
            map.put("showType", showType);
        }
        if (periodStart!=null) {
            map.put("periodStart", periodStart);

        }
        String url ="http://" +  baseUrl + "/countryCase/getCountriesByParam";
        return GetPostUtil.sendGet(url, map);
    }
}
