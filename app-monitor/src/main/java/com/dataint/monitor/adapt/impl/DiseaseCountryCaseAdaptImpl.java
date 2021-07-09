package com.dataint.monitor.adapt.impl;

import com.alibaba.fastjson.JSONObject;
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
        HashMap<String, String> map = new HashMap<>();
        if (diseaseCountryParam.getCurrent()!=null)
        map.put("current", diseaseCountryParam.getCurrent().toString());
        if (diseaseCountryParam.getPageSize()!=null)
            map.put("pageSize", diseaseCountryParam.getPageSize().toString());
        if (diseaseCountryParam.getPeriodStart() != null)
            map.put("periodStart", diseaseCountryParam.getPeriodStart());
        if (diseaseCountryParam.getShowType() != null)
            map.put("showType", diseaseCountryParam.getShowType());
        if (diseaseCountryParam.getDiseaseId() != null)
            map.put("diseaseId", diseaseCountryParam.getDiseaseId().toString());
        if (diseaseCountryParam.getCountryNameCn() != null)
            map.put("countryNameCn", diseaseCountryParam.getCountryNameCn());
        if (diseaseCountryParam.getId()!=null)
            map.put("id", diseaseCountryParam.getId().toString());



        String url ="http://" +  baseUrl + "/countryCase/diseaseCountryCaseList";
        return GetPostUtil.sendGet(url, map);
    }

    @Override
    public Object addDiseaseCountry(DiseaseCountryCase countryCase) {
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

    @Override
    public JSONObject getLatestCasesByParam(Long diseaseId, Long countryId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("diseaseId", diseaseId.toString());
        map.put("countryId", countryId.toString());

        String url ="http://" +  baseUrl + "/countryCase/getLatestCasesByParam";
        return GetPostUtil.sendGet(url, map);
    }

    @Override
    public Object getForCountryRisk(int disease, int country, int week) {
        HashMap<String, String> map = new HashMap<>();
        map.put("diseaseId", String.valueOf(disease));
        map.put("countryId", String.valueOf(country));
        map.put("week",String.valueOf(week));

        String url ="http://" +  baseUrl + "/countryCase/getDiseaseForCountryRisk";
        return GetPostUtil.sendGet(url, map);
    }
}
