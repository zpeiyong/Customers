package com.dataint.monitor.adapt.impl;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.adapt.IArticleAdapt;
import com.dataint.monitor.dao.IArticleLikeDao;
import com.dataint.monitor.dao.ICommentDao;
import com.dataint.monitor.model.form.ArticleUpdateForm;
import com.dataint.monitor.model.param.ArticleListQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ArticleAdaptImpl implements IArticleAdapt {

    @Value("${service.datapack.baseUrl}")
    private String baseUrl;

    @Autowired
    private IArticleLikeDao articleLikeDao;

    @Autowired
    private ICommentDao commentDao;

    @Override
    public Object queryBasicList(PageParam pageParam) {
        String url = "http://" +  baseUrl + "/article/queryBasicList";
        HashMap<String, String> map = new HashMap<>();
        map.put("current", pageParam.getCurrent().toString());
        map.put("pageSize", pageParam.getPageSize().toString());
        return GetPostUtil.sendGet(url,map);
    }

    @Override
    public Object queryBasicById(Long id) {
        String url = "http://" +  baseUrl + "/article/basic/" + id.toString();
        return GetPostUtil.sendGet(url);
    }

    @Override
    public Object queryMapBasicList(Long countryId, String diseaseName, PageParam pageParam) {
        String url = "http://" +  baseUrl + "/article/queryMapBasicList";
        HashMap<String, String> map = new HashMap<>();
        map.put("countryId", countryId.toString());
        map.put("diseaseName", diseaseName);
        map.put("current",pageParam.getCurrent().toString());
        map.put("pageSize", pageParam.getPageSize().toString());
        return GetPostUtil.sendGet(url, map);
    }

    @Override
    public JSONObject getArticleList(ArticleListQueryParam articleListQueryParam) {
        HashMap<String, String> map = new HashMap<>();
        map.put("current", articleListQueryParam.getCurrent().toString());
        map.put("pageSize", articleListQueryParam.getPageSize().toString());
        if (articleListQueryParam.getKeyword() != null)
            map.put("keyword", articleListQueryParam.getKeyword());
        if (articleListQueryParam.getArticleType() != null)
            map.put("articleType", articleListQueryParam.getArticleType());
        if (articleListQueryParam.getCrawlTimeStart() != null)
            map.put("crawlTimeStart", articleListQueryParam.getCrawlTimeStart());
        if (articleListQueryParam.getCrawlTimeEnd() != null)
            map.put("crawlTimeEnd", articleListQueryParam.getCrawlTimeEnd());
        if (articleListQueryParam.getDiseaseId() != null)
            map.put("diseaseId", articleListQueryParam.getDiseaseId().toString());
        if (articleListQueryParam.getMediaTypeId() != null)
            map.put("mediaTypeId", articleListQueryParam.getMediaTypeId().toString());
        if (articleListQueryParam.getRegionId() != null)
            map.put("regionId", articleListQueryParam.getRegionId().toString());
        
        String url ="http://" +  baseUrl + "/article/normal/getArticleList";

        return GetPostUtil.sendGet(url, map);
    }

    @Override
    public Object getArticleById(Long id) {
        String url = "http://" +  baseUrl + "/article/normal/" + id.toString();
        return GetPostUtil.sendGet(url);
    }

    @Override
    public Object delArticles(String idListStr) {
        String url = "http://" +  baseUrl + "/normal/delArticles?idListStr=" + idListStr;
        return GetPostUtil.sendDelete(url);
    }

    @Override
    public Object addKeyword(String idListStr, String keyword) {
        String url = "http://" +  baseUrl + "/addKeyword";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idListStr", idListStr);
        jsonObject.put("keyword", keyword);
        String jsonString = jsonObject.toJSONString();
        return GetPostUtil.sendPut(url, jsonString, 3000);
    }

    @Override
    public Object delKeyword(Long id, String keyword) {
        String url = "http://" +  baseUrl + "/delKeyword/" + id.toString()+"?keyword="+keyword;
        return GetPostUtil.sendDelete(url);
    }

    @Override
    public Object updateLevel(Long id, Long levelId) {
        String url = "http://" + baseUrl + "/updateLevel/" + id.toString() + "?levelId=" + levelId.toString();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("levelId", levelId);
        String jsonString = jsonObject.toJSONString();
        return GetPostUtil.sendPut(url, jsonString, 3000);
    }

    @Override
    public Object updateArticle(ArticleUpdateForm articleUpdateForm) {
        String url = "http://" + baseUrl + "/updateArticle";
        String jsonString = JSONObject.toJSONString(articleUpdateForm);
        return GetPostUtil.sendPut(url, jsonString, 3000);
    }

    @Override
    public Object queryReportContent(String startTime, String endTime, String type) {
        String url = "http://" + baseUrl + "/queryReport";
        HashMap<String, String> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("type", type);
        return GetPostUtil.sendGet(url, map);
    }

    @Override
    public JSONObject queryArticlesByIdList(List<Long> articleIdList) {
        String idListStr = StringUtils.join(articleIdList, ",");

        String url = "http://" + baseUrl + "/article/queryArticlesByIdList";
        HashMap<String, String> map = new HashMap<>();
        map.put("idListStr", idListStr);
        return GetPostUtil.sendGet(url, map);
    }
}
