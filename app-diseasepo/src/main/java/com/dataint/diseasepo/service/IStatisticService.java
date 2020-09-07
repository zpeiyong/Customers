package com.dataint.diseasepo.service;

import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.diseasepo.model.*;

import java.util.List;

public interface IStatisticService {

    /**
     * 获取BI首页基本统计信息
     * @return
     */
    BasicInfoVO getBasicInfo();

    /**
     *
     * @return
     */
    List<BIMonthlyByTimeVO> getMonthlyByTime();

    /**
     *
     * @return
     */
    List<BIMonthlyByCountryVO> getMonthlyByCountry();

    /**
     *
     * @return
     */
    List<ImpactRankVO> getImpactRank();

    /**
     *
     * @return
     */
    List<MapInfoVO> getMapInfo();

    /**
     *
     * @param countryId
     * @param diseaseName
     * @return
     */
    MapDetailVO getMapDetail(Integer countryId, String diseaseName, PageParam pageParam);

    /*
    自动统计(每日/每周/每月)
     */
    void dailyStatistic(String dailyStartTime, String dailyEndTime, String year1AgoStartTime);

}
