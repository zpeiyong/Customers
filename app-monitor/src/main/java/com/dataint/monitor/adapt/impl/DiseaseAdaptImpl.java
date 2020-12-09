package com.dataint.monitor.adapt.impl;

import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.adapt.IDiseaseAdapt;
import com.dataint.monitor.model.param.DiseaseQueryParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class DiseaseAdaptImpl implements IDiseaseAdapt {

    @Value("${service.datapack.baseUrl}")
    private String baseUrl;


    @Override
    public Object queryFocusDiseases(DiseaseQueryParam diseaseQueryParam) {
        HashMap<String, String> map = new HashMap<>();
        map.put("current", diseaseQueryParam.getCurrent().toString());
        map.put("pageSize", diseaseQueryParam.getPageSize().toString());
        if (diseaseQueryParam.getKeyword() != null)
            map.put("keyword", diseaseQueryParam.getKeyword());
        String url ="http://" +  baseUrl + "/disease/queryFocus";
        return GetPostUtil.sendGet(url, map);
    }

    @Override
    public Object getCasesByCountryId(Long countryId, String dateStr, PageParam pageParam) {
        HashMap<String, String> map = new HashMap<>();
        map.put("current", pageParam.getCurrent().toString());
        map.put("pageSize", pageParam.getPageSize().toString());
        map.put("countryId", countryId.toString());
        if (dateStr != null)
            map.put("dateStr", dateStr);
        String url ="http://" +  baseUrl + "/disease/getCasesByCountryId/"+ countryId.toString();
        return GetPostUtil.sendGet(url, map);
    }

    @Override
    public Object getCasesByCidAndDid(Long countryId, Long diseaseId, String dateStr) {
        HashMap<String, String> map = new HashMap<>();
        map.put("countryId", countryId.toString());
        map.put("diseaseId", diseaseId.toString());
        if (dateStr != null)
            map.put("dateStr", dateStr);
        String url ="http://" +  baseUrl + "/disease/getCasesByCidAndDid";
        return GetPostUtil.sendGet(url, map);
    }
}
