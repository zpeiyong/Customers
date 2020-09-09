package com.dataint.monitor.provider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.monitor.model.form.ArticleUpdateForm;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public interface ArticleProvider {

    /* BI */

    ResultVO getLatestList(Integer current, Integer pageSize);


    ResultVO getArticleBasicById(Integer id);


    ResultVO<JSONArray> queryMapBasicList(Integer countryId,
                                String diseaseName,
                                Integer current,
                                Integer pageSize);


    /* Web */
    ResultVO<JSONObject> getArticleList(Integer current, Integer pageSize, String articleType );

    ResultVO getArticleById(Integer id);

    ResultVO delArticle(String idListStr);

    ResultVO<JSONArray> addKeyword(String idListStr,String keyword);

    ResultVO<JSONObject> delKeyword( Integer id,String keyword);

    ResultVO updateLevel(Integer id, Integer levelId);

    ResultVO<JSONObject> updateArticle(ArticleUpdateForm articleUpdateForm);

    ResultVO<JSONObject> queryDailyReport(String startTime, String endTime, String type);

    ResultVO<List<String>> searchByKeyword( String keyword);
}
