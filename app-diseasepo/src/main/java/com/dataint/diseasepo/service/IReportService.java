package com.dataint.diseasepo.service;

import com.dataint.diseasepo.model.ReportVO;
import com.dataint.diseasepo.model.param.ReportQueryParam;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;

public interface IReportService {
    /*
    web api
     */

    /**
     *
     * @param reportQueryParam
     * @return
     */
    Page<ReportVO> queryReportList(ReportQueryParam reportQueryParam);

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
    Resource loadFileAsResource(Integer reportId);


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
