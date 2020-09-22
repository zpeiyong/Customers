package com.dataint.topic.service;



public interface IStatisticService {

    void hourlyStatistic(String startTime, String endTime);

    void dailyStatistic(String startTime, String endTime);

    void eventStatistic(String startTime, String endTime);

    Object getSpreadSpeed(Integer keywordId, int countDays);

    Object getSpreadRange(Integer keywordId, int countDays);

    Object periodHotPOStat(String startTime, String endTime);

    Object sendPeriodHotPOStat(String startTime, String endTime);

    Object sendPeriodDetails(String beginTime, String overTime);

    // StatisticTasks
    Boolean sendStatistic(String startTime, String endTime);

    void sendHotPODetails(String startTime, String endTime);

}
