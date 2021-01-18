package com.dataint.monitor.adapt.impl;

import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.adapt.IDictAdapt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DictAdaptImpl implements IDictAdapt {

    @Value("${service.datapack.baseUrl}")
    private String baseUrl;


    @Override
    public Object queryRegions() {
        String url ="http://" +  baseUrl + "/dict/queryRegions";
        return GetPostUtil.sendGet(url);
    }

    @Override
    public Object queryCountries() {
        String url ="http://" +  baseUrl + "/dict/queryCountries";
        return GetPostUtil.sendGet(url);
    }

    @Override
    public Object queryMediaTypes() {
        String url ="http://" +  baseUrl + "/dict/queryMediaTypes";
        return GetPostUtil.sendGet(url);
    }

    @Override
    public Object queryArticleTypes() {
        String url ="http://" +  baseUrl + "/dict/queryArticleTypes";
        return GetPostUtil.sendGet(url);
    }
}
