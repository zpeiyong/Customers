package com.dataint.monitor.crontab;

import com.dataint.cloud.common.model.Constants;
import com.dataint.cloud.common.utils.DateUtil;
import com.dataint.monitor.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReportTasks {

    @Autowired
    private IReportService reportService;

    @Scheduled(cron = "0 5 0 * * ?")
    public void generateDailyReport() {
        Long currentTimestamps=System.currentTimeMillis();
        System.out.println("----- [crontab]generateDailyReport(" + Constants.DateTimeSDF.format(currentTimestamps) + ") -----");

        // 获取生成日报的起始时间
        String dailyStartTime = DateUtil.getYesterdayStart();
        String dailyEndTime = DateUtil.getYesterdayEnd();

        reportService.generateDailyReport(dailyStartTime, dailyEndTime);
    }

//    @Scheduled(cron = "0 8 0 ? * ?")
//    public void generateWeeklyReport() {
//        Long currentTimestamps=System.currentTimeMillis();
//        System.out.println("----- [crontab]generateWeeklyReport(" + Constants.DateTimeSDF.format(currentTimestamps) + ") -----");
//
//        // 获取生成日报的起始时间
//        String weekStartTime = DateUtil.getLastWeekStart();
//        String weekEndTime = DateUtil.getLastWeekEnd();
//
//        reportService.generateWeeklyReport(weekStartTime, weekEndTime);
//    }
}
