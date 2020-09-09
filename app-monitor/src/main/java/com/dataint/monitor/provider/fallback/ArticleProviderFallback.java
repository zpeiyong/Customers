package com.dataint.monitor.provider.fallback;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.cloud.common.utils.JSONUtil;
import com.dataint.monitor.model.form.ArticleUpdateForm;
import com.dataint.monitor.provider.ArticleProvider;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@Slf4j
public class ArticleProviderFallback implements ArticleProvider {
@Value("${service.datapack.baseUrl}")
private String baseUrl;

    @Override
    public ResultVO getLatestList(Integer current, Integer pageSize) {
        JSONObject jsonObject = GetPostUtil.sendGet(baseUrl + "/article/queryBasicList");
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO getArticleBasicById(Integer id) {
        JSONObject jsonObject = GetPostUtil.sendGet(baseUrl + "/article/basic/{id}");
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO<JSONArray> queryMapBasicList(Integer countryId, String diseaseName, Integer current, Integer pageSize) {
        JSONObject jsonObject = GetPostUtil.sendGet(baseUrl + "/article/queryMapBasicList");
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO<JSONObject> getArticleList(Integer current, Integer pageSize, String articleType) {
        JSONObject jsonObject = GetPostUtil.sendGet(baseUrl + "/article/normal/getArticleList");
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO getArticleById(Integer id) {
        JSONObject jsonObject = GetPostUtil.sendGet(baseUrl + "/article/getArticleById");
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO delArticle(@RequestParam("idListStr") String idListStr) {
        JSONObject jsonObject = GetPostUtil.sendDelete(baseUrl + "/article/normal/delArticles");
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO<JSONArray> addKeyword(String idListStr, String keyword) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("keyword", keyword);
        jsonObject.put("idListStr", JSONArray.parseArray(idListStr));
        JSONObject jsonObjects = GetPostUtil.sendPost(baseUrl + "/article/addKeyword", JSONUtil.toJSon(jsonObject), 8000);
        return ResultVO.success(jsonObjects);
    }

    @Override
    public ResultVO<JSONObject> delKeyword(Integer id, String keyword) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("keyword",keyword);
        JSONObject jsonObjects = GetPostUtil.sendPut(baseUrl + "/article/delKeyword/{id}", JSONUtil.toJSon(jsonObject), 8000);
        return ResultVO.success(jsonObjects);
    }

    @Override
    public ResultVO updateLevel(Integer id, Integer levelId) {
        JSONObject jsonObject =new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("levelId",levelId);
        JSONObject jsonObjects = GetPostUtil.sendPut(baseUrl + "/article/updateLevel/{id}", JSONUtil.toJSon(jsonObject), 8000);
        return ResultVO.success(jsonObjects);
    }

    @Override
    public ResultVO<JSONObject> updateArticle(ArticleUpdateForm articleUpdateForm) {
        JSONObject jsonObject = GetPostUtil.sendPut(baseUrl + "/article/updateArticle", JSONUtil.toJSon(articleUpdateForm), 8000);
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO<JSONObject> queryDailyReport(String startTime, String endTime, String type) {
        JSONObject jsonObject = GetPostUtil.sendGet(baseUrl + "/article/queryReport");
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO<List<String>> searchByKeyword(String keyword) {
        log.warn("ArticleProvider.searchByKeyword() failed");
        JSONObject jsonObject = GetPostUtil.sendGet(baseUrl + "/article/searchByKeyword");
        return ResultVO.success(jsonObject);
    }

}
