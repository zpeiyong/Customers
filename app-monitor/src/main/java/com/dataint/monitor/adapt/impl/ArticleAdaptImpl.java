package com.dataint.monitor.adapt.impl;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.adapt.IArticleAdapt;
import com.dataint.monitor.model.form.ArticleUpdateForm;
import com.dataint.monitor.model.form.StoreDataForm;
import com.dataint.monitor.model.param.ArticleListQueryParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ArticleAdaptImpl implements IArticleAdapt {

    @Value("${service.datapack.baseUrl}")
    private String baseUrl;

    @Override
    public Object storeData(StoreDataForm storeDataForm) {
        String url ="http://" +  baseUrl + "/article/store";
        String jsonString = JSONObject.toJSONString(storeDataForm);
        return GetPostUtil.sendPost(url, jsonString);
    }

    @Override
    public Object queryBasicList(PageParam pageParam) {
        return null;
    }

    @Override
    public Object queryBasicById(Long id) {
        return null;
    }

    @Override
    public Object queryMapBasicList(Long countryId, String diseaseName, PageParam pageParam) {
        return null;
    }

    @Override
    public Object getArticleList(ArticleListQueryParam articleListQueryParam) {
        return null;
    }

    @Override
    public Object getArticleById(Long id) {
        return null;
    }

    @Override
    public Object delArticles(String idListStr) {
        return null;
    }

    @Override
    public Object addKeyword(String idListStr, String keyword) {
        return null;
    }

    @Override
    public Object delKeyword(Long id, String keyword) {
        return null;
    }

    @Override
    public Object updateLevel(Long id, Long levelId) {
        return null;
    }

    @Override
    public Object updateArticle(ArticleUpdateForm articleUpdateForm) {
        return null;
    }

    @Override
    public Object searchByKeyword(String keyword) {
        return null;
    }

    @Override
    public Object queryReportContent(String startTime, String endTime, String type) {
        return null;
    }
}
