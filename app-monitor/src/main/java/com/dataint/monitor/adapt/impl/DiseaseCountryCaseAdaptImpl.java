package com.dataint.monitor.adapt.impl;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.adapt.IDiseaseCountryCaseAdapt;
import com.dataint.monitor.model.DiseaseCountryCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class DiseaseCountryCaseAdaptImpl implements IDiseaseCountryCaseAdapt {
    @Value("${service.datapack.baseUrl}")
    private String baseUrl;
    @Override
    public Object listDiseaseCountry(String diseaseNameCn, String countryNameCn, String showType, Date periodStart) {
        HashMap<String, String> map = new HashMap<>();
        if (diseaseNameCn!=null){
            map.put("diseaseNameCn", diseaseNameCn);
        }
        if (countryNameCn!=null){
        map.put("countryNameCn", countryNameCn);
        }
        if (showType!=null){
        map.put("showType", showType);
        }
        if (periodStart!=null) {
            map.put("periodStart", periodStart.toString());
        }
        String url ="http://" +  baseUrl + "/countryCase/diseaseCountryCaseList";
        return GetPostUtil.sendGet(url, map);
    }

    @Override
    public Object addDieaseCountry(DiseaseCountryCase countryCase) {
        String url ="http://" +  baseUrl + "/countryCase/addDiseaseCountry";
        String jsonString = JSONObject.toJSONString(countryCase);
        return GetPostUtil.sendPost(url, jsonString);
    }
}
