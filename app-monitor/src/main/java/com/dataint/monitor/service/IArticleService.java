package com.dataint.monitor.service;

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
    ResultVO getArticleBasicById(Long articleId);

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
    ResultVO getArticleById(Long userId, Long id, String systemType);

    /**
     *
     * @param idList
     * @return
     */
    Object delArticles(String idList);

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
    Object addKeyword(Long userId, String idList, String keyword);

    /**
     *
     * @param id
     * @param keyword
     * @return
     */
    Object delKeyword(Long userId, Long id, String keyword);

    /**
     *
     * @param id
     * @param levelId
     * @return
     */
    Object updateLevel(Long id, Long levelId);

    /**
     *
     * @param userId
     * @param articleUpdateForm
     * @return
     */
    JSONObject updateArticle(Long userId, ArticleUpdateForm articleUpdateForm);

    /**
     *
     * @param diseaseId
     * @param pageSize
     * @param current
     * @param releaseTime
     * @return
     */
    JSONObject queryEventList(Long diseaseId,Long pageSize, Long current, String releaseTime,String searchTime);

}
