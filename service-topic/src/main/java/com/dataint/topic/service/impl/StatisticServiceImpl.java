package com.dataint.topic.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.Constants;
import com.dataint.cloud.common.utils.DateUtil;
import com.dataint.topic.db.*;
import com.dataint.topic.db.dao.*;
//import com.dataint.topic.db.entity.PoKeyword;
import com.dataint.topic.db.entity.Statistic;
import com.dataint.topic.model.po.SendModelStatistic;
import com.dataint.topic.model.vo.StatisticRangeVO;
import com.dataint.topic.service.IStatisticService;
import com.dataint.topic.utils.SendDataUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
public class StatisticServiceImpl implements IStatisticService {
    @Autowired
    private IArticleDao articleDao;

    @Autowired
    private IDynamicDataDao dynamicDataDao;

    @Autowired
    private IMediaDao mediaDao;

    @Autowired
    private IMediaTypeDao mediaTypeDao;

    @Autowired
    private IEventDao eventDao;

    @Autowired
    private IStatisticDao statisticDao;

//    @Autowired
//    private IPoKeywordDao poKeywordDao;

    private static String HOURLY = "hourly";
    private static String DAILY = "daily";
    private static Integer EVENT_INTERACTION_THRESHOLD = 1000;

    @Getter
    enum EventEnum {
        ONE_THOUSAND(1, "微博转发、评论、点赞数超过1000!"),
        TWO_THOUSAND(2, "微博转发、评论、点赞数超过2000!"),
        FIVE_THOUSAND(3, "微博转发、评论、点赞数超过5000!"),
        GOV_MEDIA_FIRST(4, "官方媒体首次报道!");

        private int index;
        private String name;

        EventEnum(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public static String getName(int index) {
            for (EventEnum e : EventEnum.values()) {
                if (e.getIndex() == index)
                    return e.name;
            }
            return null;
        }
    }

    @Override
    public void hourlyStatistic(String startTime, String endTime) {
        Map<Integer, Statistic> statisticMap = new HashMap<>();

        /*
         *  get/set statistic results
         */
        // get/set articleCnt, websiteCnt and other info
        List<IBaseArticle> articleCntList = articleDao.countArticlesAndWebsite(startTime, endTime);
        for (IBaseArticle baseArticle : articleCntList) {
//            Statistic statistic = new Statistic();
//
//            Integer keywordId = baseArticle.getKeywordId();
//            statistic.setStatisticType(HOURLY);
//            statistic.setKeywordId(keywordId);
//            statistic.setArticleCnt(baseArticle.getArticleCnt());
//            statistic.setWebsiteCnt(baseArticle.getWebsiteCnt());
//
//            try {
//                statistic.setStartTime(Constants.DateTimeSDF.parse(startTime));
//                statistic.setEndTime(Constants.DateTimeSDF.parse(endTime));
//                statistic.setStatisticTime(Constants.DateTimeSDF.parse(Constants.DateTimeSDF.format(new Date())));
//            } catch (ParseException pe) {
//                pe.printStackTrace();
//            }

//            statisticMap.put(keywordId, statistic);
        }

        // get/set forwardCnt, commentCnt, likeCnt
        List<IBaseInteraction> interactionCntList = dynamicDataDao.countInteractions(startTime, endTime);
//        for (IBaseInteraction baseInteraction : interactionCntList) {
//            Integer keywordId = baseInteraction.getKeywordId();
//            Statistic statistic = statisticMap.get(keywordId);
//
//            //
//            if (baseInteraction.getForwardCnt() != null)
//                statistic.setForwardCnt(baseInteraction.getForwardCnt());
//            if (baseInteraction.getCommentCnt() != null)
//                statistic.setCommentCnt(baseInteraction.getCommentCnt());
//            if (baseInteraction.getLikeCnt() != null)
//                statistic.setLikeCnt(baseInteraction.getLikeCnt());
//
//            statisticMap.put(keywordId, statistic);
//        }

        // get/set mediaCnt
        statisticMap = getMediaCount(statisticMap, startTime, endTime);

        // save statisticMap
        for (Statistic statistic : statisticMap.values())
            save(statistic);

    }

    @Override
    public void dailyStatistic(String startTime, String endTime) {
        Map<Integer, Statistic> statisticMap = new HashMap<>();

        // get/set articleCnt, forwardCnt, commentCnt, likeCnt and other info
        List<IBaseStatistic> statisticList = statisticDao.countTotalStatistic(startTime, endTime, HOURLY);
//        for (IBaseStatistic baseStatistic : statisticList) {
//            Statistic statistic = new Statistic();
//
//            Integer keywordId = baseStatistic.getKeywordId();
//            statistic.setStatisticType(DAILY);
//            statistic.setKeywordId(keywordId);
//            statistic.setArticleCnt(baseStatistic.getArticleCnt());
//            statistic.setForwardCnt(baseStatistic.getForwardCnt());
//            statistic.setCommentCnt(baseStatistic.getCommentCnt());
//            statistic.setLikeCnt(baseStatistic.getLikeCnt());
//
//            try {
//                statistic.setStartTime(Constants.DateTimeSDF.parse(startTime));
//                statistic.setEndTime(Constants.DateTimeSDF.parse(endTime));
//                statistic.setStatisticTime(Constants.DateTimeSDF.parse(Constants.DateTimeSDF.format(new Date())));
//            } catch (ParseException pe) {
//                pe.printStackTrace();
//            }
//
//            statisticMap.put(keywordId, statistic);
//        }

        // get/set websiteCnt
        List<IBaseArticle> articleCntList = articleDao.countArticlesAndWebsite(startTime, endTime);
//        for (IBaseArticle baseArticle : articleCntList) {
//            Integer keywordId = baseArticle.getKeywordId();
//            Statistic statistic = statisticMap.get(keywordId);
//
//            statistic.setWebsiteCnt(baseArticle.getWebsiteCnt());
//
//            statisticMap.put(keywordId, statistic);
//        }

        // get/set mediaCnt
        statisticMap = getMediaCount(statisticMap, startTime, endTime);

        // save statisticMap
        for (Statistic statistic : statisticMap.values())
            save(statistic);

    }

    @Override
    public void eventStatistic(String startTime, String endTime) {
        /*
         * check and append to Topic
         */
        // interactionCnt
        List<IBaseInteraction> totalCntList = statisticDao.countTotalInteraction(HOURLY);
//        for (IBaseInteraction baseInteraction : totalCntList) {
//            Integer keywordId = baseInteraction.getKeywordId();
//
//            // check if exist
//            Topic tmp = eventDao.getEventByKeywordIdAndSubTitle(keywordId, EventEnum.getName(1));
//            if (tmp != null)
//                continue;
//
//            // > 1000
//            if (baseInteraction.getTotalCnt() >= EVENT_INTERACTION_THRESHOLD) {
//                Topic event = new Topic();
//                event.setKeywordId(keywordId);
//                event.setEventTime(new Date());
//                event.setEventType("1");  // 1:舆情热度值关键节点
//                event.setSubTitle(EventEnum.getName(1));
//                event.setGmtCreate(new Date());
//
//                eventRepository.save(event);
//            }
//        }

        // govMedia for first time
        List<Integer> keywordIdList = articleDao.getKeywordIdList(startTime, endTime);
//        for (Integer keywordId : keywordIdList) {
//            // get first article
//            TopicArticle article = articleRepository.findTop1ByKeywordIdOrderByGmtCrawl(keywordId);
//            if (article != null) {
//                // check if exist
//                Topic tmp = eventDao.getEventByKeywordIdAndSubTitle(article.getKeywordId(), EventEnum.getName(4));
//
//                if (tmp != null)
//                    continue;
//
//                MediaType mediaType = mediaTypeRepository.getMediaTypeByMediaTypeNameLike(article.getAuthor());
//                if (mediaType != null && mediaType.getMediaTypeId() == 1) {
//                    // add Topic()
//                    Topic event = new Topic();
//                    event.setKeywordId(article.getKeywordId());
//                    event.setEventTime(new Date());
//                    event.setEventType("1");  // 1:舆情热度值关键节点
//                    event.setSubTitle(EventEnum.getName(4));
//                    event.setTitle(article.getTitle());
//                    event.setMediaType(mediaType);
//                    event.setSummary(article.getSummary());
//
//                    eventRepository.save(event);
//                }
//            }
//        }
    }

    @Override
    public Object getSpreadSpeed(Integer keywordId, int countDays) {
        List<Map<String, String>> respList = buildRespList(countDays);

        List<ISpreadSpeed> speedList = statisticDao.countSpreadSpeed(keywordId, HOURLY, countDays);
        for (ISpreadSpeed item : speedList) {
            respList.forEach((e) -> {
                if (item.getStartTime().equals(e.get("startTime"))) {
                    e.put("value", item.getTotalCnt() + "");
                }
            });
        }

        return respList;
    }

    @Override
    public Object getSpreadRange(Integer keywordId, int countDays) {
        // get all startTimes since <countDays> ago
        List<Date> startTimeList = getDateList(countDays);

        // initialize dataList
        List<Map<String, String>> dataList = new ArrayList<>();
        List<String> nameList = Arrays.asList("官方媒体", "新闻媒体", "微博", "微信");
        for (String name : nameList) {
            Map<String, String> map = new HashMap<String, String>(){{
//                for (Date date : startTimeList) {
//                    put(Constants.DateTimeSDF.format(date), "0");
//                }
//                put("name", name);
            }};
            dataList.add(map);
        }

        // countSpreadRange
        List<Statistic> statisticList = statisticDao.countSpreadRange(keywordId, HOURLY, startTimeList);
        for (Statistic statistic : statisticList) {
//            for (Map<String, String> map : dataList) {
//                // startTime
//                String startTime = Constants.DateTimeSDF.format(statistic.getStartTime());
//                // name
//                String name = map.get("name");
//
//                if ("官方媒体".equals(name))
//                    map.put(startTime, statistic.getGovMediaCnt()+"");
//                if ("新闻媒体".equals(name))
//                    map.put(startTime, statistic.getSelfMediaCnt()+"");
//                if ("微博".equals(name))
//                    map.put(startTime, statistic.getWbMediaCnt()+"");
//                if ("微信".equals(name))
//                    map.put(startTime, statistic.getWxMediaCnt()+"");
//            }
        }

        // build return type
        StatisticRangeVO statisticRangeVO = new StatisticRangeVO();
        statisticRangeVO.setFields(startTimeList);
        statisticRangeVO.setData(dataList);

        return statisticRangeVO;
    }

    @Override
    public Object periodHotPOStat(String beginTime, String overTime) {
        boolean ifDaily = false;
        // verify time period
        try {
            Date beginDate = Constants.DateTimeSDF.parse(beginTime);
            Date overDate = Constants.DateTimeSDF.parse(overTime);
//            if (overDate.getTime() - beginDate.getTime() < Long.valueOf(60*60*12*1000))  // 12 hours
//                return ResultUtil.buildFailMap("时间间隔需至少相隔12小时!");
//            else if (overDate.getTime() - beginDate.getTime() >= Long.valueOf(60*60*24*1000))  // 24 hours
//                // 时间间隔大于24小时, 则进行日统计到overTime的前一天
//                ifDaily = true;
        } catch (ParseException pe) {
            pe.printStackTrace();
            return null;
        }

        // hourly statistic
        List<Map<String, String>> hourlyList = DateUtil.getHourlyBetweenIntervals(beginTime, overTime);
        for (Map<String, String> hourlyMap : hourlyList) {
            String startTime = hourlyMap.get("startTime");
            String endTime = hourlyMap.get("endTime");

            hourlyStatistic(startTime, endTime);
        }

        // daily statistic
        if (ifDaily) {
            List<Map<String, String>> dailyList = DateUtil.getDailyBetweenIntervals(beginTime, overTime);
            for (Map<String, String> dailyMap : dailyList) {
                String startTime = dailyMap.get("startTime");
                String endTime = dailyMap.get("endTime");

                dailyStatistic(startTime, endTime);
            }
        }

//        return ResultUtil.buildSuccResultMap();
        return null;
    }

    /* 暂时不实现，只定时发送excel数据 */
    @Override
    public Object sendPeriodHotPOStat(String beginTime, String overTime) {
        List<Map<String, String>> intervalList = DateUtil.getHourlyBetweenIntervals(beginTime, overTime);

        return null;
    }

    @Override
    public Object sendPeriodDetails(String beginTime, String overTime) {
        List<Map<String, String>> list = DateUtil.getDailyBetweenIntervals(beginTime, overTime);

        for (Map<String, String> map : list) {
            String startTime = map.get("startTime");
            String endTime = map.get("endTime");

            sendHotPODetails(startTime, endTime);
        }

//        return ResultUtil.buildSuccResultMap();
        return null;
    }


    // StatisticTasks.sendData()
    @Override
    public Boolean sendStatistic(String startTime, String endTime) {
        boolean ifSuccess = false;

        List<SendModelStatistic> statisticList = new ArrayList<>();
        // read from excel(xlsx)
        try {
            statisticList = SendDataUtil.getSendModelStatistic();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // read from database
//        Statistic statistic = statisticRepository.getByKeywordIdAndStartTimeAndEndTimeAndStatisticType();

        // send
        if (statisticList != null && statisticList.size() > 0) {
            for (SendModelStatistic sendModelStatistic : statisticList) {
                JSONObject sendDataJO = new JSONObject();
                sendDataJO.put("type", "CUSTOMS_YQ_TIMENODEINFO");
                sendDataJO.put("data", sendModelStatistic.convertToJSONObject());

//                // send to bdCenter
//                JSONObject responseJO = RequestUtil.postToShhgBDCenter(sendDataJO);
////                System.out.println(sendDataJO.toJSONString());
//
//                if (responseJO != null) {
//                    if ("200".equals(responseJO.getString("code"))) {
//                        System.out.println(responseJO.getString("msg"));
//
//                        // success
//                        ifSuccess = true;
//                    } else {
//                        System.out.println("发送热点统计信息数据失败! " + startTime);
//                        System.out.println(responseJO.getString("msgError"));
//                    }
//                }

                // make delay
                try {
                    Thread.sleep(1000 * 10);  // 10s
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }

        return false;
    }

    // StatisticTasks.sendData()
    @Override
    public void sendHotPODetails(String startTime, String endTime) {
//        List<PoKeyword> poKeywordList = poKeywordDao.findAllByIfSend("1");
//        // 构建将要发送的数据JO
//        JSONObject sendDataJO = new JSONObject();
//        sendDataJO.put("type", "CUSTOMS_YQ_ARTICLEINFO");

//        for (PoKeyword poKeyword : poKeywordList) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("KEYWORD_ID", poKeyword.getKeywordId()+"");
//            jsonObject.put("KEYWORDS", poKeyword.getKeyword());
//            jsonObject.put("YQSTAT_TIME", Constants.DateTimeSDF.format(new Date()));
//
//            // set articleInfo
//            int currentPage = 1;
//            int pageSize = 50;
//            boolean flag = true;
//            while (flag) {
//                //
//                sendDataJO.remove("data");
//                jsonObject.remove("ARTICLEINFO");
//
//                // get Articles by keywordId
//                Page<TopicArticle> articlePage = null;
//                try {
//                    articlePage = articleRepository.getArticlesByKeywordIdAndGmtCrawlBetween(
//                            poKeyword.getKeywordId(), Constants.DateTimeSDF.parse(startTime), Constants.DateTimeSDF.parse(endTime),
//                            PageRequest.of(currentPage-1, pageSize, Sort.by("gmtCreate")));
//                } catch (ParseException pe) {
//                    pe.printStackTrace();
//                }
//
//                if (articlePage != null) {
//                    List<TopicArticle> articleList = articlePage.getContent();
//
//                    if (articleList.size() > 0) {
//                        JSONArray articleJA = new JSONArray();
//
//                        for (TopicArticle article : articleList) {
//                            JSONObject articleJO = new JSONObject();
//                            articleJO.put("ARTICLE_ID", article.getArticleId()+"");
//                            articleJO.put("ARTICLE_URL", article.getArticleUrl());
//                            articleJO.put("AUTHOR", article.getAuthor());
//                            articleJO.put("GMT_PUBLISH", Constants.DateTimeSDF.format(article.getGmtRelease()));
//                            articleJO.put("TITLE", article.getTitle());
//                            if (article.getSummary() != null && !article.getSummary().isEmpty()) {
//                                String summary = article.getSummary();
//                                if (article.getSummary().length() >= 500) {
//                                    summary = summary.substring(0, 497) + "...";
//                                }
//                                articleJO.put("SUMMARY", summary);
//                            }
//
//                            articleJA.add(articleJO);
//                        }
//
//                        //
//                        jsonObject.put("ARTICLEINFO", articleJA);
//                        sendDataJO.put("data", jsonObject);
//
//                        // send to bdCenter
//                        JSONObject resJO = RequestUtil.postToShhgBDCenter(sendDataJO);
////                        System.out.println(sendDataJO);
//
//                        //
//                        if (resJO != null) {
//                            if ("200".equals(resJO.getString("code"))) {
//                                System.out.println(startTime + " - " + endTime + " : " + poKeyword.getKeyword());
//                                System.out.println(resJO.getString("msg"));
//                            } else {
//                                System.out.println("发送热点舆情详细数据失败! " + startTime);
//                                System.out.println(resJO.getString("msgError"));
//                            }
//                        }
//                    }
//
//                    //
//                    if (articlePage.isLast())
//                        flag = false;
//                    else
//                        currentPage += 1;
//                } else {
//                    flag = false;
//                }
//
//                // make delay
//                try {
//                    Thread.sleep(1000 * 10);  // 10s
//                } catch (InterruptedException ie) {
//                    ie.printStackTrace();
//                }
//            }
//        }
    }

    /**
     * get and set mediaCnt/selfMediaCnt/wbMediaCnt/wxMediaCnt
     *
     * @param statisticMap
     * @param startTime
     * @param endTime
     * @return
     */
    private Map<Integer, Statistic> getMediaCount(Map<Integer, Statistic> statisticMap, String startTime, String endTime) {
        // govMediaCnt, selfMediaCnt
        List<IBaseMedia> mediaCntList = mediaDao.countMedia(startTime, endTime);
        for (IBaseMedia baseMedia : mediaCntList) {
            Integer keywordId = baseMedia.getKeywordId();
            Statistic statistic = statisticMap.get(keywordId);

            // govMediaCnt/....
            Integer notSelfMediaCnt = 0;
            Integer mediaType = baseMedia.getMediaTypeId();
//            if (mediaType == 1) {
//                statistic.setGovMediaCnt(baseMedia.getMediaCnt());
//                notSelfMediaCnt += baseMedia.getMediaCnt();
//            }

            // selfMediaCnt
//            Integer websiteCnt = statistic.getWebsiteCnt();  // media/author/..
//            statistic.setSelfMediaCnt(websiteCnt - notSelfMediaCnt);
//
//            statisticMap.put(keywordId, statistic);
        }

        // wbMediaCnt
        List<IBaseMedia> wbMediaCntList = mediaDao.countWbWxMedia(startTime, endTime, "微博");
//        for (IBaseMedia baseMedia : wbMediaCntList) {
//            Integer keywordId = baseMedia.getKeywordId();
//            Statistic statistic = statisticMap.get(keywordId);
//
//            statistic.setWbMediaCnt(baseMedia.getMediaCnt());
//
//            statisticMap.put(keywordId, statistic);
//        }

        // wxMediaCnt
        List<IBaseMedia> wxMediaCntList = mediaDao.countWbWxMedia(startTime, endTime, "微信");
//        for (IBaseMedia baseMedia : wxMediaCntList) {
//            Integer keywordId = baseMedia.getKeywordId();
//            Statistic statistic = statisticMap.get(keywordId);
//
//            statistic.setWxMediaCnt(baseMedia.getMediaCnt());
//
//            statisticMap.put(keywordId, statistic);
//        }

        return statisticMap;
    }

    /**
     * save Object<statistic>
     * @param statistic
     */
    private void save(Statistic statistic) {
//        // check if already exist
//        Statistic tmp = statisticRepository.getByKeywordIdAndStartTimeAndEndTimeAndStatisticType(statistic.getKeywordId(),
//                statistic.getStartTime(), statistic.getEndTime(), statistic.getStatisticType());
//
//        // save
//        if (tmp == null)
//            statisticRepository.save(statistic);
    }

    private List<Map<String, String>> buildRespList(Integer days) {
        List<Map<String, String>> respList = new ArrayList<>(days);

        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)-days, 0, 0, 0);
        int hour = 0;
        for (int i=days; i>0; i--) {
//            System.out.println(Constants.DateTimeSDF.format(calendar.getTime()));
            Map<String, String> dataMap = new HashMap<>(2);
            dataMap.put("startTime", Constants.DateTimeSDF.format(calendar.getTime()));
            dataMap.put("value", "0");
            respList.add(dataMap);

            if (hour == 0) {
                calendar.add(Calendar.HOUR_OF_DAY, 12);
                hour += 12;
                i += 1;
            } else {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                hour -= 12;
            }
        }

        return respList;
    }

    private List<Date> getDateList(Integer days) {
        List<Date> dateList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)-days, 0, 0, 0);
        int hour = 0;
        for (int i=days; i>0; i--) {
            try {
                dateList.add(Constants.DateTimeSDF.parse(Constants.DateTimeSDF.format(calendar.getTime())));
            } catch (ParseException pe) {
                pe.printStackTrace();
            }

            if (hour == 0) {
                calendar.add(Calendar.HOUR_OF_DAY, 12);
                hour += 12;
                i += 1;
            } else {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                hour -= 12;
            }
        }

        return dateList;
    }
}
