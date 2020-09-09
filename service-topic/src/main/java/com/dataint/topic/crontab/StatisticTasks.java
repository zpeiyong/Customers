package com.dataint.topic.crontab;

import com.dataint.cloud.common.model.Constants;
import com.dataint.topic.service.IStatisticService;
import com.dataint.topic.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Calendar;

@Component
public class StatisticTasks extends SimpleAsyncTaskExecutor {
    @Autowired
    private IStatisticService statisticService;

    private static Long intervalTimestamps= Long.valueOf(60*60*12*1000);  // 12 hours
    private static Long oneDayTimestamps = Long.valueOf(60*60*24*1000);  // 1 day

//    @Scheduled(cron = "0 5 0,12 * * ?")
    public void hourlyStatistic() {
        Long currentTimestamps = System.currentTimeMillis();
        System.out.println("----- [crontab]hourlyStatistic("+Constants.DateTimeSDF.format(currentTimestamps)+") -----");

        // last interval startTime/endTime
        try {
            // startTime
            Calendar startCal = DateUtil.getStartTimeOfInterval(Constants.DateTimeSDF.format(currentTimestamps-intervalTimestamps));

            // endTime
            Calendar endCal = DateUtil.getStartTimeOfInterval(Constants.DateTimeSDF.format(currentTimestamps));
            endCal.add(Calendar.MILLISECOND, -1);

            // statistic
            statisticService.hourlyStatistic(Constants.DateTimeSDF.format(startCal.getTime()),
                    Constants.DateTimeSDF.format(endCal.getTime()));
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

//    @Scheduled(cron = "0 20 0 * * ?")
    public void dailyStatistic() {
        Long currentTimestamps=System.currentTimeMillis();
        System.out.println("----- [crontab]dailyStatistic("+Constants.DateTimeSDF.format(currentTimestamps)+") -----");

        // yesterday
        try {
            // startTime
            Calendar startCal = DateUtil.getStartTimeOfDay(Constants.DateTimeSDF.format(currentTimestamps-oneDayTimestamps));

            // endTime
            Calendar endCal = DateUtil.getStartTimeOfInterval(Constants.DateTimeSDF.format(currentTimestamps));
            endCal.add(Calendar.MILLISECOND, -1);

            // statistic
            statisticService.dailyStatistic(Constants.DateTimeSDF.format(startCal.getTime()),
                    Constants.DateTimeSDF.format(endCal.getTime()));
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

//    @Scheduled(cron = "0 15 0,12 * * ?")
    public void eventStatistic() {
        Long currentTimestamps = System.currentTimeMillis();
        System.out.println("----- [crontab]eventStatistic("+Constants.DateTimeSDF.format(currentTimestamps)+") -----");

        // last interval startTime/endTime
        try {
            // startTime
            Calendar startCal = DateUtil.getStartTimeOfInterval(Constants.DateTimeSDF.format(currentTimestamps-intervalTimestamps));

            // endTime
            Calendar endCal = DateUtil.getStartTimeOfInterval(Constants.DateTimeSDF.format(currentTimestamps));
            endCal.add(Calendar.MILLISECOND, -1);

            // statistic
            statisticService.eventStatistic(Constants.DateTimeSDF.format(startCal.getTime()),
                    Constants.DateTimeSDF.format(endCal.getTime()));
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

//    @Scheduled(cron = "0 28 0,12 * * ?")
    public void sendStatistic() {

        boolean ifSuccess = statisticService.sendStatistic(DateUtil.getYesterdayStart(), DateUtil.getYesterdayEnd());
    }

//    @Scheduled(cron = "0 29 0,12 * * ?")
    public void sendDetails() {

        statisticService.sendHotPODetails(DateUtil.getYesterdayStart(), DateUtil.getYesterdayEnd());
    }
}
