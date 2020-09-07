package com.dataint.service.datapack.service;

import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.service.datapack.model.ArticleBasicVO;
import com.dataint.service.datapack.model.ArticleVO;
import com.dataint.service.datapack.model.form.ArticleUpdateForm;
import com.dataint.service.datapack.model.form.StoreDataForm;
import com.dataint.service.datapack.model.params.ArticleListQueryParam;
import org.springframework.data.domain.Page;

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
    List<ArticleBasicVO> queryBasicList(PageParam pageParam);

    /**
     *
     * @param articleId
     * @return
     */
    ArticleBasicVO queryBasicById(Integer articleId);

    /**
     *
     * @param countryId
     * @param diseaseName
     * @param pageParam
     * @return
     */
    List<ArticleBasicVO> queryMapBasicList(Integer countryId, String diseaseName, PageParam pageParam);


    /* Web疫情讯息模块 */
    /**
     *
     * @param pageParam
     * @return
     */
    Page<Object> getArticleList(ArticleListQueryParam articleListQueryParam);

    /**
     *
     * @param articleId
     * @return
     */
    ArticleVO getArticleById(Integer articleId);

    /**
     *
     * @param articleId
     */
    void delArticleById(Integer articleId);

    /**
     * 添加舆情关键词(单条/批量)
     * @param idList
     * @param keyword
     * @return
     */
    List<ArticleBasicVO> addKeyword(List<Integer> idList, String keyword);

    /**
     * 删除舆情关键词(仅可单条)
     * @param id
     * @param keyword
     * @return
     */
    ArticleBasicVO delKeyword(Integer id, String keyword);

    /**
     *
     * @param articleId
     * @param levelId
     * @return
     */
    ArticleBasicVO updateLevel(Integer articleId, Integer levelId);

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
     * @param startTime
     * @param endTime
     * @param type
     * @return
     */
    Map<String, Object> queryReportContent(String startTime, String endTime, String type);
}
