package com.dataint.monitor.adapt.impl;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.adapt.ICrawlSiteAdapt;
import com.dataint.monitor.model.CrawlSiteVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CrawlSiteAdaptImpl implements ICrawlSiteAdapt {

    @Value("${service.topic.baseUrl}")
    private String baseUrl;

    @Override
    public Object getCrawlSiteList() {
        String url ="http://" +  baseUrl + "/crawlsite/getCrawlSiteList";
        return GetPostUtil.sendGet(url);
    }

    @Override
    public Object getCrawlSiteNames() {
        String url ="http://" +  baseUrl + "/crawlsite/getCrawlSiteNames";
        return GetPostUtil.sendGet(url);
    }

    @Override
    public Object getCrawlSiteById(Integer siteId) {
        String url ="http://" +  baseUrl + "/crawlsite/getCrawlSiteById/" + siteId.toString();
        return GetPostUtil.sendGet(url);
    }

    @Override
    public Object addCrawlSite(CrawlSiteVO crawlSiteVO) {
        String url ="http://" +  baseUrl + "/crawlsite/add";
        String jsonString = JSONObject.toJSONString(crawlSiteVO);
        return GetPostUtil.sendPost(url, jsonString);
    }

    @Override
    public Object updateCrawlSiteById(Integer siteId, CrawlSiteVO crawlSiteVO) {
        String url ="http://" +  baseUrl + "/crawlsite/update/" + siteId.toString();
        String jsonString = JSONObject.toJSONString(crawlSiteVO);
        return GetPostUtil.sendPost(url, jsonString);
    }

    @Override
    public Object deleteCrawlSiteById(Integer siteId) {
        String url ="http://" +  baseUrl + "/crawlsite/delete/" + siteId.toString();
        return GetPostUtil.sendDelete(url);
    }
}
