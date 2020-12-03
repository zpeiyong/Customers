package com.dataint.monitor.adapt;

import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.monitor.model.form.ArticleUpdateForm;
import com.dataint.monitor.model.form.StoreDataForm;
import com.dataint.monitor.model.param.ArticleListQueryParam;

public interface IArticleAdapt {
    Object storeData(StoreDataForm storeDataForm);

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

    Object searchByKeyword(String keyword);

    Object queryReportContent(String startTime, String endTime, String type);
}
