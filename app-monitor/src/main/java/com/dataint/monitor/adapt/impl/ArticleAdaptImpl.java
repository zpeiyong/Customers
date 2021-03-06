package com.dataint.monitor.adapt.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.adapt.IArticleAdapt;
import com.dataint.monitor.model.form.ArticleUpdateForm;
import com.dataint.monitor.model.param.ArticleListQueryParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class ArticleAdaptImpl implements IArticleAdapt {

    @Value("${service.datapack.baseUrl}")
    private String baseUrl;

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
    public Object queryMapBasicList(Long countryId, Long diseaseId, String searchTime, PageParam pageParam) {
        String url = "http://" +  baseUrl + "/article/queryMapBasicList";
        HashMap<String, String> map = new HashMap<>();
        if (countryId != null) {
            map.put("countryId", countryId.toString());
        }
        if (!StringUtils.isEmpty(searchTime)) {
            map.put("searchTime", searchTime);
        }
        map.put("diseaseId", diseaseId.toString());
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
    public JSONObject getArticleById(Long id) {
        String url = "http://" +  baseUrl + "/article/normal/" + id.toString();
        return GetPostUtil.sendGet(url);
    }

    @Override
    public JSONObject getSimilarArticlesById(Long id, PageParam pageParam) {
        HashMap<String, String> map = new HashMap<>();
        map.put("current", pageParam.getCurrent().toString());
        map.put("pageSize", pageParam.getPageSize().toString());

        String url = "http://" +  baseUrl + "/article/normal/similar/" + id.toString();
        return GetPostUtil.sendGet(url, map);
    }

    @Override
    public Object delArticles(String idListStr) {
        String url = "http://" +  baseUrl + "/article/normal/delArticles";
        JSONObject jsonObject  = new JSONObject();
        jsonObject.put("idListStr", idListStr);
        String jsonString = jsonObject.toJSONString();
        return GetPostUtil.sendPut(url, jsonString, 3000);
    }

    @Override
    public Object addKeyword(String idListStr, String keyword) {
        String url = "http://" +  baseUrl + "/article/addKeyword";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idListStr", idListStr);
        jsonObject.put("keyword", keyword);
        String jsonString = jsonObject.toJSONString();
        return GetPostUtil.sendPut(url, jsonString, 3000);
    }

    @Override
    public Object delKeyword(Long id, String keyword) {
        String url = "http://" +  baseUrl + "/article/delKeyword/"+id.toString();
        JSONObject jsonObject  = new JSONObject();
        jsonObject.put("keyword", keyword);
        String jsonString = jsonObject.toJSONString();
        return GetPostUtil.sendPut(url, jsonString,3000 );
    }

    @Override
    public Object updateLevel(Long id, Long levelId) {
        String url = "http://" + baseUrl + "/article/updateLevel/" + id.toString();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("levelId", levelId);
        String jsonString = jsonObject.toJSONString();
        return GetPostUtil.sendPut(url, jsonString, 3000);
    }

    @Override
    public JSONObject updateArticle(ArticleUpdateForm articleUpdateForm) {
        String url = "http://" + baseUrl + "/article/updateArticle";
        String jsonString = JSONObject.toJSONString(articleUpdateForm);
        return GetPostUtil.sendPut(url, jsonString, 3000);
    }

    @Override
    public Object queryEventList(Long diseaseId,Long pageSize, Long current,String  releaseTime,String searchTime) {
        String url = "http://" + baseUrl + "/article/queryEventList";
        HashMap<String, String> map = new HashMap<>();
        if (diseaseId != null)
            map.put("diseaseId", diseaseId.toString());
        if (pageSize != null)
            map.put("pageSize", pageSize.toString());
        if (current != null)
            map.put("current", current.toString());
        if (releaseTime != null)
            map.put("releaseTime", releaseTime);
        if (searchTime!=null)
            map.put("searchTime", searchTime);
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

    public List<String> getKeywordsByFoDiseaseName(String  fDisName){

        String url = "http://" + baseUrl + "/article/getKeywordsByFoDiseaseName";
        HashMap<String, String> map = new HashMap<>();
        map.put("diseaseName", fDisName);

        if(log.isDebugEnabled()) {
            log.debug("url : {}",url);
        }

        JSONObject result = GetPostUtil.sendGet(url,map);
        if(result.containsKey("data")) {
           JSONArray jsonArray = result.getJSONArray("data");

           if(!jsonArray.isEmpty()) {

               return jsonArray.toJavaList(String.class);
           }
        }

        return null;
    }
}
