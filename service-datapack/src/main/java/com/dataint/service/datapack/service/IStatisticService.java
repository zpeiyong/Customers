package com.dataint.service.datapack.service;

import com.alibaba.fastjson.JSONObject;
import com.dataint.service.datapack.model.vo.StatisticBasicBIVO;
import com.dataint.service.datapack.model.vo.StatisticBasicVO;

import java.util.List;
import java.util.Map;

public interface IStatisticService {

    /**
     *
     * @param startTime
     * @param endTime
     * @return
     */
    JSONObject getPeriodBasic(String startTime, String endTime);

    /**
     *
     * @return
     */
    long getArticleTotal();

    /**
     *
     * @param startTime
     * @param endTime
     * @param countryId
     * @return
     */
    JSONObject getMapInfoByCountry(String startTime, String endTime, Long countryId);

    /**
     *
     * @return
     */
    StatisticBasicVO getStatisticBasic(Long diseaseId);

    /**
     *
     * @return
     */
    StatisticBasicBIVO getStatisticBasicBI(Long diseaseId);

    /**
     *
     * @param diseaseId
     * @param days
     * @return
     */
    List<Map<String, Object>> getCountryAddTimeLine(Long diseaseId, String dateStr, Integer days);

    /**
     *
     * @param diseaseId
     * @return
     */
    List<Map<String, Object>> getCountryRiskRank(Long diseaseId, String dateStr);

    /**
     *
     * @param diseaseId
     * @param days
     * @return
     */
    List<Map<String, Object>> getEventAddTimeLine(Long diseaseId, String dateStr, Integer days);

    /**
     *
     * @param diseaseId
     * @return
     */
    List<Map<String, Object>> getEventTotalCntRank(Long diseaseId, String dateStr);

    /**
     *
     * @param diseaseId
     * @param days
     * @return
     */
    List<Map<String, Object>> getArticleAddTimeLine(Long diseaseId, String dateStr, Integer days);

    /**
     *
     * @param diseaseId
     * @return
     */
    List<Map<String, Object>> getArticleTotalCntRank(Long diseaseId, String dateStr);

    /**
     *
     * @param diseaseId
     * @param dateStr
     * @param days
     * @return
     */
    List<Map<String, Object>> getArticleAddTimeLineByType(Long diseaseId, String dateStr, Integer days);

    /**
     *
     * @return
     */
    List<Map<String, Object>> getMapCountryList();

    /**
     *
     * @param dateStr
     * @param days
     * @return
     */
    List<Map<String, Object>> getGlobalRiskTimeLine(String dateStr, Integer days);

    /**
     *
     * @param diseaseId
     * @param dateStr
     * @return
     */
    List<Map<String, Object>> getDeathTotalCntRank(Long diseaseId, String dateStr);

    /**
     *
     * @param diseaseId
     * @param dateStr
     * @param days
     * @return
     */
    List<Map<String, Object>> getDeathTimeLine(Long diseaseId, String dateStr, Integer days);

    /**
     *
     * @param diseaseId
     * @param dateStr
     * @param days
     * @return
     */
    List<Map<String, Object>> getConfirmedTimeLine(Long diseaseId, String dateStr, Integer days);

    /**
     *
     * @param diseaseId
     * @param dateStr
     * @return
     */
    List<Map<String, Object>> getConfirmedTotalCntRank(Long diseaseId, String dateStr);

    /**
     *
     * @param diseaseId
     * @param dateStr
     * @param days
     * @return
     */
    List<Map<String, Object>> getCuredTimeLine(Long diseaseId, String dateStr, Integer days);

    /**
     *
     * @param diseaseId
     * @param dateStr
     * @return
     */
    List<Map<String, Object>> getCuredTotalCntRank(Long diseaseId, String dateStr);

}
