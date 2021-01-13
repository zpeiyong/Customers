package com.dataint.monitor.service;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.monitor.model.param.ReportQueryParam;
import org.springframework.core.io.Resource;

public interface IReportService {
    /*
    web api
     */

    /**
     *
     * @param reportQueryParam
     * @return
     */
    ResultVO queryReportList(ReportQueryParam reportQueryParam);

    /**
     *
     * @param beginTime
     * @param overTime
     */
    void genPeriodReport(String beginTime, String overTime);

    /**
     *
     * @param reportId
     * @return
     */
    Resource loadFileAsResource(Long reportId);


    /*
    crontab tasks
     */
    /**
     *
     * @param todayStartTime
     * @param todayEndTime
     */
    void generateDailyReport(String todayStartTime, String todayEndTime);

    /**
     *
     * @param weekStartTime
     * @param weekEndTime
     */
    void generateWeeklyReport(String weekStartTime, String weekEndTime);

}
