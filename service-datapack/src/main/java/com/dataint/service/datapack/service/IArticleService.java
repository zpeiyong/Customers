package com.dataint.service.datapack.service;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.service.datapack.model.form.ArticleUpdateForm;
import com.dataint.service.datapack.model.form.StoreDataForm;
import com.dataint.service.datapack.model.param.ArticleListQueryParam;
import com.dataint.service.datapack.model.vo.ArticleBasicVO;
import com.dataint.service.datapack.model.vo.ArticleReportVO;
import com.dataint.service.datapack.model.vo.ArticleVO;
import com.dataint.service.datapack.model.vo.BIArticleBasicVO;

import java.util.List;
import java.util.Map;

public interface IArticleService {

    /**
     * 数据存储接口
     * @param storeDataForm
     */
    void storeData(StoreDataForm storeDataForm);


    /* BI展示模块 */
    /**
     *
     * @param pageParam
     * @return
     */
    ResultVO queryBasicList(PageParam pageParam);

    /**
     *
     * @param articleId
     * @return
     */
    BIArticleBasicVO queryBasicById(Long articleId);

    /**
     *
     * @param countryId
     * @param diseaseId
     * @param pageParam
     * @return
     */
    ResultVO queryMapBasicList(Long countryId, Long diseaseId, String searchTime, PageParam pageParam);


    /* Web疫情讯息模块 */
    /**
     *
     * @param articleListQueryParam
     * @return
     */
    ResultVO getArticleList(ArticleListQueryParam articleListQueryParam);

    /**
     *
     * @param articleId
     * @return
     */
    ArticleVO getArticleById(Long articleId);

    /**
     *
     * @param articleId
     * @param pageParam
     * @return
     */
    ResultVO getSimilarArticlesById(Long articleId, PageParam pageParam);

    /**
     *
     * @param articleId
     */
    void delArticleById(Long articleId);

    /**
     * 添加舆情关键词(单条/批量)
     * @param idList
     * @param keyword
     * @return
     */
    List<ArticleBasicVO> addKeyword(List<Long> idList, String keyword);

    /**
     * 删除舆情关键词(仅可单条)
     * @param id
     * @param keyword
     * @return
     */
    ArticleBasicVO delKeyword(Long id, String keyword);

    /**
     *
     * @param articleUpdateForm
     * @return
     */
    ArticleVO updateArticle(ArticleUpdateForm articleUpdateForm);



    /* 统计/分析模块 */
    List<String> searchByKeyword(String keyword);

    /**
     *
     * @param articleIdList
     * @return
     */
    List<ArticleReportVO> queryReportContent(List<Long> articleIdList);

    List<Map<String, Object>> queryEventList(Long diseaseId, int pageSize, int current, String  releaseTime,String searchTime);

    /**
     * 根据疾病名称查询关键字
     * @param fDisName
     * @return
     */
    List<String> getKeywordsByFoDiseaseName(String  fDisName);
}
