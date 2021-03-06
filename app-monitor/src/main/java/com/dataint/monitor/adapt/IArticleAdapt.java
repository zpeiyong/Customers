package com.dataint.monitor.adapt;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.monitor.model.form.ArticleUpdateForm;
import com.dataint.monitor.model.param.ArticleListQueryParam;

import java.util.List;

public interface IArticleAdapt {

    Object queryBasicList(PageParam pageParam);

    Object queryBasicById(Long id);

    Object queryMapBasicList(Long countryId, Long diseaseId, String searchTime, PageParam pageParam);

    JSONObject getArticleList(ArticleListQueryParam articleListQueryParam);

    JSONObject getArticleById(Long id);

    Object getSimilarArticlesById(Long id, PageParam pageParam);

    Object delArticles(String idListStr);

    Object addKeyword(String idListStr, String keyword);

    Object delKeyword(Long id, String keyword);

    Object updateLevel(Long id, Long levelId);

    JSONObject updateArticle(ArticleUpdateForm articleUpdateForm);

    Object queryEventList(Long diseaseId,Long pageSize, Long current,String  releaseTime,String searchTime);

    JSONObject queryArticlesByIdList(List<Long> articleIdList);

    List<String> getKeywordsByFoDiseaseName(String  fDisName);


}
