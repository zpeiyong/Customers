package com.dataint.monitor.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.monitor.model.form.ArticleUpdateForm;
import com.dataint.monitor.model.param.ArticleListQueryParam;

import java.util.List;

public interface IArticleService {

    /**
     *
     * @param pageParam
     * @return
     */
    ResultVO getLatestList(PageParam pageParam);

    /**
     *
     * @param articleId
     * @return
     */
    ResultVO getArticleBasicById(Integer articleId);

    /**
     * 
     * @param articleListQueryParam
     * @param userId
     * @param systemType
     * @return
     */
    ResultVO getArticleList(ArticleListQueryParam articleListQueryParam, Long userId, String systemType);

    /**
     *
     * @param userId
     * @param id
     * @return
     */
    JSONObject getArticleById(Integer userId, Integer id);

    /**
     *
     * @param idList
     * @return
     */
    ResultVO delArticles(List<Integer> idList);

    /**
     *
     * @param userId
     * @param articleIdList
     * @return
     */
    boolean updateFavorites(Integer userId, List<Integer> articleIdList);

    /**
     *
     * @param idList
     * @param keyword
     * @return
     */
    JSONArray addKeyword(Integer userId, List<Integer> idList, String keyword);

    /**
     *
     * @param id
     * @param keyword
     * @return
     */
    JSONObject delKeyword(Integer userId, Integer id, String keyword);

    /**
     *
     * @param id
     * @param levelId
     * @return
     */
    ResultVO updateLevel(Integer id, Integer levelId);

    /**
     *
     * @param userId
     * @param articleUpdateForm
     * @return
     */
    JSONObject updateArticle(int userId, ArticleUpdateForm articleUpdateForm);
}
