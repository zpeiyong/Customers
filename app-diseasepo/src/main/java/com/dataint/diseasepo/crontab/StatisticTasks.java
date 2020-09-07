package com.dataint.diseasepo.crontab;

import com.dataint.cloud.common.model.Constants;
import com.dataint.cloud.common.utils.DateUtil;
import com.dataint.diseasepo.service.IStatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
@Slf4j
public class StatisticTasks {

    @Autowired
    private IStatisticService statisticService;

    @Scheduled(cron = "0 10 0 * * ?")
    public void dailyStatistic() {
        Long currentTimestamps = System.currentTimeMillis();
        System.out.println("----- [crontab]dailyStatistic(" + Constants.DateTimeSDF.format(currentTimestamps) + ") -----");

        // 统计
        try {
            String yesStartTime = DateUtil.getYesterdayStart();
            String yesEndTime = DateUtil.getYesterdayEnd();
            String year1AgoStartTime = DateUtil.get1YearAgoStart(yesStartTime);

            statisticService.dailyStatistic(yesStartTime, yesEndTime, year1AgoStartTime);

        } catch (ParseException pe) {
            log.error("每日统计捕获异常 -- " + Constants.DateTimeSDF.format(currentTimestamps));
            pe.printStackTrace();
        }
    }
}