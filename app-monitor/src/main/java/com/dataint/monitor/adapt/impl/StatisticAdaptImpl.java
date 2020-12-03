package com.dataint.monitor.adapt.impl;

import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.adapt.IStatisticAdapt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class StatisticAdaptImpl implements IStatisticAdapt {

    @Value("${service.datapack.baseUrl}")
    private String baseUrl;


    @Override
    public Object getStatisticBasic(Long diseaseId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("diseaseId", diseaseId.toString());
        String url ="http://" +  baseUrl + "/statistic/getStatisticBasic";
        return GetPostUtil.sendGet(url, map);
    }

    @Override
    public Object getStatisticBasicBI(Long diseaseId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("diseaseId", diseaseId.toString());
        String url ="http://" +  baseUrl + "/statistic/getStatisticBasicBI";
        return GetPostUtil.sendGet(url, map);
    }

    @Override
    public Object getCountryAddTimeLine(Long diseaseId, String dateStr) {
        HashMap<String, String> map = new HashMap<>();
        map.put("diseaseId", diseaseId.toString());
        map.put("dateStr", dateStr);
        String url ="http://" +  baseUrl + "/statistic/country/getAddTimeLine";
        return GetPostUtil.sendGet(url, map);
    }

    @Override
    public Object getCountryRiskRank(Long diseaseId, String dateStr) {
        HashMap<String, String> map = new HashMap<>();
        map.put("diseaseId", diseaseId.toString());
        map.put("dateStr", dateStr);
        String url ="http://" +  baseUrl + "/statistic/country/getRiskRank";
        return GetPostUtil.sendGet(url, map);
    }

    @Override
    public Object getEventAddTimeLine(Long diseaseId, String dateStr) {
        HashMap<String, String> map = new HashMap<>();
        map.put("diseaseId", diseaseId.toString());
        map.put("dateStr", dateStr);
        String url ="http://" +  baseUrl + "/statistic/event/getAddTimeLine";
        return GetPostUtil.sendGet(url, map);
    }

    @Override
    public Object getEventTotalCntRank(Long diseaseId, String dateStr) {
        HashMap<String, String> map = new HashMap<>();
        map.put("diseaseId", diseaseId.toString());
        map.put("dateStr", dateStr);
        String url ="http://" +  baseUrl + "/statistic/event/getTotalCntRank";
        return GetPostUtil.sendGet(url, map);
    }

    @Override
    public Object getArticleAddTimeLine(Long diseaseId, String dateStr) {
        HashMap<String, String> map = new HashMap<>();
        map.put("diseaseId", diseaseId.toString());
        map.put("dateStr", dateStr);
        String url ="http://" +  baseUrl + "/statistic/article/getAddTimeLine";
        return GetPostUtil.sendGet(url, map);
    }

    @Override
    public Object getArticleTotalCntRank(Long diseaseId, String dateStr) {
        HashMap<String, String> map = new HashMap<>();
        map.put("diseaseId", diseaseId.toString());
        map.put("dateStr", dateStr);
        String url ="http://" +  baseUrl + "/statistic/article/getTotalCntRank";
        return GetPostUtil.sendGet(url, map);
    }

    @Override
    public Object getMapCountryList() {
        String url ="http://" +  baseUrl + "/statistic/getMapCountryList";
        return GetPostUtil.sendGet(url);
    }

    @Override
    public Object getGlobalRiskTimeLine(String dateStr) {
        HashMap<String, String> map = new HashMap<>();
        map.put("dateStr", dateStr);
        String url ="http://" +  baseUrl + "/statistic/getGlobalRiskTimeLine";
        return GetPostUtil.sendGet(url, map);
    }
}
