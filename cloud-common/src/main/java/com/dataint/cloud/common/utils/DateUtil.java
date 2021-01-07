package com.dataint.cloud.common.utils;

import com.dataint.cloud.common.model.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class DateUtil {
    /**
     * 获取当前时间  yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentTime() {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return Constants.getDateTimeFormat().format(new Date());// new Date()为获取当前系统时间
    }
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

        return Constants.getDateTimeFormat().format(cal.getTime());
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

        return Constants.getDateTimeFormat().format(cal.getTime());
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

        return Constants.getDateTimeFormat().format(cal.getTime());
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

        return Constants.getDateTimeFormat().format(cal.getTime());
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

        return Constants.getDateTimeFormat().format(cal.getTime());
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

        return Constants.getDateTimeFormat().format(cal.getTime());
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
        return Constants.getDateTimeFormat().format(cal.getTime());
    }

    /**
     * 获取指定时间前一天 00:00:00
     * @param timeStr
     * @return
     * @throws ParseException
     */
    public static String getYesterdayStart(String timeStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(timeStr));
        cal.add(Calendar.DAY_OF_YEAR, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return Constants.getDateTimeFormat().format(cal.getTime());
    }

    /**
     * 获取指定时间前一天 23:59:59
     * @param timeStr
     * @return
     * @throws ParseException
     */
    public static String getYesterdayEnd(String timeStr) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Constants.getDateTimeFormat().parse(timeStr));
        cal.add(Calendar.DAY_OF_YEAR, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        return Constants.getDateTimeFormat().format(cal.getTime());
    }

    /**
     * 获取上周的第一天 00:00:00
     * @return
     */
    public static String getLastWeekStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -7);
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        cal.add(Calendar.DATE, -day_of_week + 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return Constants.getDateTimeFormat().format(cal.getTime());
    }


    /**
     * 获取指定时间的上周的第一天 00:00:00
     * @return
     */
    public static String getLastWeekStart(String timeStr) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Constants.getDateTimeFormat().parse(timeStr));
        cal.add(Calendar.DAY_OF_YEAR, -7);
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        cal.add(Calendar.DATE, -day_of_week + 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return Constants.getDateTimeFormat().format(cal.getTime());
    }

    /**
     * 获取上周的最后一天 00:00:00
     * @return
     */
    public static String getLastWeekEnd() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -7);
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        cal.add(Calendar.DATE, -day_of_week + 7);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 59);

        return Constants.getDateTimeFormat().format(cal.getTime());
    }

    /**
     * 获取指定时间所在周的第一天 00:00:00
     * @param timeStr
     * @return
     */
    public static String getWeekStart(String timeStr) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Constants.getDateTimeFormat().parse(timeStr));
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        cal.add(Calendar.DATE, -day_of_week + 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return Constants.getDateTimeFormat().format(cal.getTime());
    }

    /**
     * 获取指定时间所在周的最后一天 23:59:59
     * @param timeStr
     * @return
     */
    public static String getWeekEnd(String timeStr) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Constants.getDateTimeFormat().parse(timeStr));
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        cal.add(Calendar.DATE, -day_of_week + 7);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 59);

        return Constants.getDateTimeFormat().format(cal.getTime());
    }

    /**
     * 获取7天前 00:00:00
     * @return
     */
    public static String get7DaysAgoStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -7);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return Constants.getDateTimeFormat().format(cal.getTime());
    }

    /**
     * 获取当月第一天0点
     * @return
     */
    public static String getStartTimeOfQuarter() {
        LocalDate nowDate = LocalDate.now();

        LocalDate firstDayOfMonth = nowDate.with(TemporalAdjusters.firstDayOfMonth());

        return firstDayOfMonth.format(DateTimeFormatter.ofPattern(Constants.DateTimeFormat));
    }

    /**
     * 获取当季第一天0点
     * @return
     */
    public static String getStartTimeOfMonth(){
        LocalDate nowDate = LocalDate.now();
        Month firstMonthOfQuarter = nowDate.getMonth().firstMonthOfQuarter();

        LocalDate firstDayOfQuarter = LocalDate.of(nowDate.getYear(), firstMonthOfQuarter, 1);

        return firstDayOfQuarter.format(DateTimeFormatter.ofPattern(Constants.DateTimeFormat));
    }

    /**
     * 获取当年第一天0点
     * @return
     */
    public static String getStartTimeOfYeary() {
        LocalDate nowDate = LocalDate.now();

        LocalDate firstDayOfYear = nowDate.with(TemporalAdjusters.firstDayOfYear());

        return firstDayOfYear.format(DateTimeFormatter.ofPattern(Constants.DateTimeFormat));
    }

    /**
     * 获取某时间段内每一天的日期(Date)列表
     * @param beginDateTime
     * @param overDateTime
     * @return
     */
    public static List<String> getBetweenDate(Date beginDateTime, Date overDateTime) {
        String begin = Constants.getDateFormat().format(beginDateTime);
        String end = Constants.getDateFormat().format(overDateTime);

        List<String> betweenList = new ArrayList<>();

        try{
            Calendar startDay = Calendar.getInstance();
            startDay.setTime(Constants.getDateFormat().parse(begin));
            startDay.add(Calendar.DATE, -1);

            while(true){
                startDay.add(Calendar.DATE, 1);
                Date newDate = startDay.getTime();
                String newend = Constants.getDateFormat().format(newDate);
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
     * 获取某时间段内的间隔时间(DateTime)列表 - 12Hours
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
                String start = Constants.getDateTimeFormat().format(startCal.getTime());
                intervalMap.put("startTime", start);
                startCal.add(Calendar.HOUR_OF_DAY, 12);
                startCal.add(Calendar.MILLISECOND, -1);
                String end = Constants.getDateTimeFormat().format(startCal.getTime());
                intervalMap.put("endTime", end);
                startCal.add(Calendar.MILLISECOND, 1);

                intervalList.add(intervalMap);
                if(end.equals(Constants.getDateTimeFormat().format(endCal.getTime()))) {
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
            cal.setTime(Constants.getDateTimeFormat().parse(beginTime));
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
                String start = Constants.getDateTimeFormat().format(startCal.getTime());
                intervalMap.put("startTime", start);
                startCal.add(Calendar.DAY_OF_YEAR, 1);
                startCal.add(Calendar.MILLISECOND, -1);
                String end = Constants.getDateTimeFormat().format(startCal.getTime());
                intervalMap.put("endTime", end);
                startCal.add(Calendar.MILLISECOND, 1);

                intervalList.add(intervalMap);
                if(end.equals(Constants.getDateTimeFormat().format(endCal.getTime()))) {
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
        timeCal.setTime(Constants.getDateTimeFormat().parse(timeStr));
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
        dayCal.setTime(Constants.getDateTimeFormat().parse(timeStr));
        dayCal.set(Calendar.HOUR_OF_DAY, 0);
        dayCal.set(Calendar.MINUTE, 0);
        dayCal.set(Calendar.SECOND, 0);
        dayCal.set(Calendar.MILLISECOND, 0);

        return dayCal;
    }

    /**
     * 获取某天是当年多少周 (day of this year)
     * @param timeStr
     * @return
     */

    public static int getWeekOfYear(String timeStr) {

        Date weekStart = null;
        try {
            weekStart = Constants.getDateTimeFormat().parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();

        calendar.setFirstDayOfWeek(Calendar.MONDAY); //美国是以周日为每周的第一天 现把周一设成第一天

        calendar.setTime(weekStart);

        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取指定时间的1年前的 00:00:00
     * @return
     */
    public static String get1YearAgoStart(String timeStr) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Constants.getDateTimeFormat().parse(timeStr));
        cal.add(Calendar.YEAR, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return Constants.getDateTimeFormat().format(cal.getTime());
    }

    public static void main(String[] args)  {
//        System.out.println(getLastWeekStart());
//        System.out.println(getLastWeekEnd());
//        System.out.println(get7DaysAgoStart());

//        String beginTime = "2019-12-01 01:01:01";
//        String overTime = "2019-12-04 23:23:01";
//        System.out.println(getDailyBetweenIntervals(beginTime, overTime));
        String timeStr = "2019-12-02 00:00:00";
//        System.out.println(getWeekOfYear(timeStr));
        try {
            System.out.println(get1YearAgoStart(timeStr));

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
