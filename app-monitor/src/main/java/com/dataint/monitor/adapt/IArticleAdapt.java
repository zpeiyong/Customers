package com.dataint.monitor.adapt;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.monitor.model.form.ArticleUpdateForm;
import com.dataint.monitor.model.form.StoreDataForm;
import com.dataint.monitor.model.param.ArticleListQueryParam;

import java.util.List;

public interface IArticleAdapt {

    Object queryBasicList(PageParam pageParam);

    Object queryBasicById(Long id);

    Object queryMapBasicList(Long countryId, String diseaseName, PageParam pageParam);

    Object getArticleList(ArticleListQueryParam articleListQueryParam);

    Object getArticleById(Long id);

    Object delArticles(String idListStr);

    Object addKeyword(String idListStr, String keyword);

    Object delKeyword(Long id, String keyword);

    Object updateLevel(Long id, Long levelId);

    Object updateArticle(ArticleUpdateForm articleUpdateForm);

    Object queryReportContent(String startTime, String endTime, String type);


    JSONObject queryArticlesByIdList(List<Long> articleIdList);
}
