package com.dataint.topic.utils;

import com.dataint.topic.common.Constants;
import org.apache.tomcat.util.bcel.Const;

import java.text.ParseException;
import java.util.*;

public class DateUtil {

    /**
     * 获取今天 00:00:00
     * @return
     */
    public static String getTodayStart() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return Constants.DateTimeSDF.format(cal.getTime());
    }

    /**
     * 获取今天 23:59:59
     * @return
     */
    public static String getTodayEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        return Constants.DateTimeSDF.format(cal.getTime());
    }

    /**
     * 获取昨天 00:00:00
     * @return String
     */
    public static String getYesterdayStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return Constants.DateTimeSDF.format(cal.getTime());
    }

    /**
     * 获取昨天 23:59:59
     * @return String
     */
    public static String getYesterdayEnd() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        return Constants.DateTimeSDF.format(cal.getTime());
    }

    /**
     * 获取本周的第一天 00:00:00
     * @return String
     */
    public static String getWeekStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_MONTH, 0);
        cal.set(Calendar.DAY_OF_WEEK, 2);  // monday
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return Constants.DateTimeSDF.format(cal.getTime());
    }

    /**
     * 获取本周最后一天 23:59:59
     * @return String
     */
    public static String getWeekEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
        cal.add(Calendar.DAY_OF_WEEK, 1);  // sunday
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 59);

        return Constants.DateTimeSDF.format(cal.getTime());
    }

    /**
     * 获取下周第一天 00:00:00
     * @return String
     */
    public static String getNextWeekStart() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
        cal.add(Calendar.DAY_OF_WEEK, 1);  // sunday
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        return Constants.DateTimeSDF.format(cal.getTime());
    }

    /**
     * 获取某时间段内每一天的日期(Date)列表
     * @param beginDateTime
     * @param overDateTime
     * @return
     */
    public static List<String> getBetweenDate(Date beginDateTime, Date overDateTime) {
        String begin = Constants.DateSDF.format(beginDateTime);
        String end = Constants.DateSDF.format(overDateTime);

        List<String> betweenList = new ArrayList<>();

        try{
            Calendar startDay = Calendar.getInstance();
            startDay.setTime(Constants.DateSDF.parse(begin));
            startDay.add(Calendar.DATE, -1);

            while(true){
                startDay.add(Calendar.DATE, 1);
                Date newDate = startDay.getTime();
                String newend = Constants.DateSDF.format(newDate);
                betweenList.add(newend);
                if(end.equals(newend)){
                    break;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return betweenList;
    }

    /**
     * 获取某时间段内的间隔时间(DateTime)列表
     * @param beginTime
     * @param overTime
     * @return
     */
    public static List<Map<String, String>> getHourlyBetweenIntervals(String beginTime, String overTime) {
        List<Map<String, String>> intervalList = new ArrayList<>();

        try {
            // startTime
            Calendar startCal = getStartTimeOfInterval(beginTime);

            // endTime
            Calendar endCal = getStartTimeOfInterval(overTime);
            endCal.add(Calendar.MILLISECOND, -1);

            while(true){
                Map<String, String> intervalMap = new HashMap<>();
                String start = Constants.DateTimeSDF.format(startCal.getTime());
                intervalMap.put("startTime", start);
                startCal.add(Calendar.HOUR_OF_DAY, 12);
                startCal.add(Calendar.MILLISECOND, -1);
                String end = Constants.DateTimeSDF.format(startCal.getTime());
                intervalMap.put("endTime", end);
                startCal.add(Calendar.MILLISECOND, 1);

                intervalList.add(intervalMap);
                if(end.equals(Constants.DateTimeSDF.format(endCal.getTime()))) {
                    break;
                }
            }
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        return intervalList;
    }

    /**
     * 获取某时间段内每一天的时间(DateTime)列表
     * @param beginTime
     * @param overTime
     * @return
     */
    public static List<Map<String, String>> getDailyBetweenIntervals(String beginTime, String overTime) {
        List<Map<String, String>> intervalList = new ArrayList<>();

        try {
            // startTime
            Calendar startCal = getStartTimeOfDay(beginTime);
            // add one day if Hour_of_day > 12
            Calendar cal = Calendar.getInstance();
            cal.setTime(Constants.DateTimeSDF.parse(beginTime));
            if (cal.get(Calendar.HOUR_OF_DAY) >= 12)
                startCal.add(Calendar.DAY_OF_YEAR, 1);

            // endTime
            Calendar endCal = getStartTimeOfDay(overTime);
            endCal.add(Calendar.MILLISECOND, -1);

            // verify
            if (startCal.getTime().getTime() >= endCal.getTime().getTime())
                return intervalList;

            while(true){
                Map<String, String> intervalMap = new HashMap<>();
                String start = Constants.DateTimeSDF.format(startCal.getTime());
                intervalMap.put("startTime", start);
                startCal.add(Calendar.DAY_OF_YEAR, 1);
                startCal.add(Calendar.MILLISECOND, -1);
                String end = Constants.DateTimeSDF.format(startCal.getTime());
                intervalMap.put("endTime", end);
                startCal.add(Calendar.MILLISECOND, 1);

                intervalList.add(intervalMap);
                if(end.equals(Constants.DateTimeSDF.format(endCal.getTime()))) {
                    break;
                }
            }
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        return intervalList;
    }

    /**
     * 获取某时间latest间隔时间 (00:00:00 || 12:00:00)
     * @param timeStr
     * @return
     * @throws ParseException
     */
    public static Calendar getStartTimeOfInterval(String timeStr) throws ParseException {
        Calendar timeCal = Calendar.getInstance();
        timeCal.setTime(Constants.DateTimeSDF.parse(timeStr));
        if (timeCal.get(Calendar.HOUR_OF_DAY) < 12) {
            // AM, start from 00:00:00 of this day
            timeCal.set(Calendar.HOUR_OF_DAY, 0);
            timeCal.set(Calendar.MINUTE, 0);
            timeCal.set(Calendar.SECOND, 0);
            timeCal.set(Calendar.MILLISECOND, 0);
        } else {
            // PM, start from 12:00:00 of this day
            timeCal.set(Calendar.HOUR_OF_DAY, 12);
            timeCal.set(Calendar.MINUTE, 0);
            timeCal.set(Calendar.SECOND, 0);
            timeCal.set(Calendar.MILLISECOND, 0);
        }

        return timeCal;
    }

    /**
     * 获取某天的零点 (00:00:00 of this day)
     * @param timeStr
     * @return
     */
    public static Calendar getStartTimeOfDay(String timeStr) throws ParseException {
        Calendar dayCal = Calendar.getInstance();
        dayCal.setTime(Constants.DateTimeSDF.parse(timeStr));
        dayCal.set(Calendar.HOUR_OF_DAY, 0);
        dayCal.set(Calendar.MINUTE, 0);
        dayCal.set(Calendar.SECOND, 0);
        dayCal.set(Calendar.MILLISECOND, 0);

        return dayCal;
    }

    public static void main(String[] args) throws Exception {
        String start = "2019-08-01 8:00:01";
        String end = "2019-08-01 24:12:12";

        List<Map<String, String>> list = getDailyBetweenIntervals(start, end);
        for (Map<String, String> map : list) {
            System.out.println(map.get("startTime"));
            System.out.println(map.get("endTime"));
        }
    }
}
