package com.dataint.monitor.adapt.impl;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.adapt.IArticleAdapt;
import com.dataint.monitor.dao.IArticleLikeDao;
import com.dataint.monitor.dao.ICommentDao;
import com.dataint.monitor.model.ArticleBasicVO;
import com.dataint.monitor.model.form.ArticleUpdateForm;
import com.dataint.monitor.model.form.StoreDataForm;
import com.dataint.monitor.model.param.ArticleListQueryParam;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        JSONObject jsonObject = GetPostUtil.sendGet(url, map);
        JSONObject data = jsonObject.getJSONObject("data");
        List content = (ArrayList) data.get("content");
        ArrayList<ArticleBasicVO> articleBasicVOArrayList = new ArrayList<>();
        if (content.size() > 0) {
            Integer userId = articleListQueryParam.getUserId();
            List<Integer> idList = articleLikeDao.findArticleIdByUserId(userId);
            for (Object ob: content){
                ArticleBasicVO articleBasicVO = new ObjectMapper().convertValue(ob, ArticleBasicVO.class);
                boolean flag = idList.contains(articleBasicVO.getId().intValue());
                if (flag)
                    articleBasicVO.setIfLike(true);
                Integer i = commentDao.countByArticleId(articleBasicVO.getId().intValue());
                articleBasicVO.setReviewCount(i);
                articleBasicVOArrayList.add(articleBasicVO);
            }
            data.put("content", articleBasicVOArrayList);
            jsonObject.put("data", data);
        }
        return jsonObject;
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
