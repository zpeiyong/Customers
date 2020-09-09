package com.dataint.topic.service;

import com.dataint.topic.common.exception.ThinventBaseException;

public interface IStatisticService {

    void hourlyStatistic(String startTime, String endTime);

    void dailyStatistic(String startTime, String endTime);

    void eventStatistic(String startTime, String endTime);

    Object getSpreadSpeed(Integer keywordId, int countDays) throws ThinventBaseException;

    Object getSpreadRange(Integer keywordId, int countDays) throws ThinventBaseException;

    Object periodHotPOStat(String startTime, String endTime) throws ThinventBaseException;

    Object sendPeriodHotPOStat(String startTime, String endTime) throws ThinventBaseException;

    Object sendPeriodDetails(String beginTime, String overTime) throws ThinventBaseException;

    // StatisticTasks
    Boolean sendStatistic(String startTime, String endTime);

    void sendHotPODetails(String startTime, String endTime);

}
