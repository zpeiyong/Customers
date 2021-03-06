package com.dataint.service.datapack.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.dim.BaseExceptionEnum;
import com.dataint.cloud.common.exception.DataNotExistException;
import com.dataint.cloud.common.exception.DataintBaseException;
import com.dataint.cloud.common.model.Constants;
import com.dataint.cloud.common.utils.DateUtil;
import com.dataint.service.datapack.db.IDayDate;
import com.dataint.service.datapack.db.IMapCountry;
import com.dataint.service.datapack.db.dao.*;
import com.dataint.service.datapack.db.entity.Country;
import com.dataint.service.datapack.db.entity.DiseaseCountryCase;
import com.dataint.service.datapack.db.entity.StatisticBasic;
import com.dataint.service.datapack.model.vo.StatisticBasicBIVO;
import com.dataint.service.datapack.model.vo.StatisticBasicVO;
import com.dataint.service.datapack.service.IStatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class StatisticServiceImpl implements IStatisticService {

    @Autowired
    private IArticleDao articleDao;
    @Autowired
    private ICountryDao countryDao;
    @Autowired
    private IArticleDiseaseDao articleDiseaseDao;
    @Autowired
    private IStatisticBasicDao statisticBasicDao;
    @Autowired
    private IStatisticGlobalRiskDao statisticGlobalRiskDao;
    @Autowired
    private IDiseaseCountryPODao diseaseCountryPODao;
    @Autowired
    private IDiseaseCountryCaseDao diseaseCountryCaseDao;

    @Override
    public JSONObject getPeriodBasic(String startTime, String endTime) {
        JSONObject periodJO = new JSONObject();

//        Date startTimeDate, endTimeDate;
//        try {
//            startTimeDate = Constants.getDateTimeFormat().parse(startTime);
//            endTimeDate = Constants.getDateTimeFormat().parse(endTime);
//        } catch (ParseException e) {
//            log.error("??????????????????!");
//            e.printStackTrace();
//            return periodJO;
//        }
//
//        // ???????????????????????????
//        int articleCnt = articleDao.countByGmtCrawlBetween(startTimeDate, endTimeDate);
//
//        // ??????????????????????????????
//        List<Object[]> diseaseCntList = articleDao.queryDiseaseCnt(startTimeDate, endTimeDate);
//        int diseaseCnt = diseaseCntList.size();
//
//        // ???????????????????????????????????????()
//        Set<String> countrySet = new HashSet<>();
//        Set<String> aCountries = articleDao.findCountryCodeFromA(startTimeDate, endTimeDate);  // Article
//        Set<String> adCountries = articleDao.findCountryCodesFromAD(startTimeDate, endTimeDate);  // ArticleDisease
//        aCountries.addAll(adCountries);
//        for (String str : aCountries) {
//            countrySet.addAll(Arrays.stream(str.split(Constants.SPLITTER)).collect(Collectors.toSet()));
//        }
//        int countryCnt = countrySet.size();
//
//        /*  */
//        periodJO.put("articleCnt", articleCnt);
//        periodJO.put("diseaseCnt", diseaseCnt);
//        periodJO.put("countryCnt", countryCnt);
        return periodJO;
    }

    @Override
    public long getArticleTotal() {

        return articleDao.count();
    }

    @Override
    public JSONObject getMapInfoByCountry(String startTime, String endTime, Long countryId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject mapInfoJO = new JSONObject();

        Date startTimeDate, endTimeDate;
        try {
            startTimeDate = sdf.parse(startTime);
            endTimeDate = sdf.parse(endTime);
        } catch (ParseException e) {
            log.error("??????????????????!");
            e.printStackTrace();
            return mapInfoJO;
        }

        // ??????????????????????????????
        Optional<Country> countryOpt = countryDao.findById(countryId);
        if (!countryOpt.isPresent()) {
            throw new DataNotExistException();
        }
        Country country = countryOpt.get();

//        // ??????????????????????????????????????? - ??????(Article.id)??????
//        Set<Integer> articleIdSet = articleDao.queryArticleIdsByCountry(startTimeDate, endTimeDate, country.getCode());
//        int articleCnt = articleIdSet.size();

        // ??????????????????????????????????????? - ??????(ArticleDisease.diseaseCode)?????????
        List<Object[]> diseaseCntList = articleDiseaseDao.queryDiseaseCntByCountry(startTimeDate, endTimeDate, country.getCode());
        JSONArray diseaseCntJA = new JSONArray();
        for (Object[] objects : diseaseCntList) {
            JSONObject diseaseCntJO = new JSONObject();
            diseaseCntJO.put("diseaseName", objects[0]);
            diseaseCntJO.put("diseaseCnt", objects[1]);
            diseaseCntJA.add(diseaseCntJO);
        }

        /*  */
        JSONObject jo = new JSONObject();
        jo.put("countryCode", country.getCode());
        jo.put("countryName", country.getNameCn());
//        jo.put("articleYearlyCnt", articleCnt);
        jo.put("diseaseCntJA", diseaseCntJA);

        return jo;
    }

    @Override
    public StatisticBasicVO getStatisticBasic(Long diseaseId) {
        StatisticBasic statisticBasic = statisticBasicDao.getTopByDiseaseIdOrderByStatisticDateDesc(diseaseId);

        return new StatisticBasicVO(statisticBasic);
    }

    @Override
    public StatisticBasicBIVO getStatisticBasicBI(Long diseaseId) {
        StatisticBasic statisticBasic = statisticBasicDao.getTopByDiseaseIdOrderByStatisticDateDesc(diseaseId);

        return new StatisticBasicBIVO(statisticBasic);
    }

    @Override
    public List<Map<String, Object>> getCountryAddTimeLine(Long diseaseId, String dateStr, Integer days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if (!StringUtils.isEmpty(dateStr)) {
            try {
                date = sdf.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new DataintBaseException(BaseExceptionEnum.DATE_PARSE_ERROR);
            }
        }
        List<Map<String, Object>> respList = buildRespList(date, days);

        List<IDayDate> dayList = statisticBasicDao.getDailyCountryAddByDiseaseId(diseaseId, days);
        for (IDayDate item : dayList) {
            respList.forEach((e) -> {
                if (item.getDay().equals(e.get("day"))) {
                    e.put("value", item.getCnt());
                }
            });
        }

        return respList;
    }

    @Override
    public List<Map<String, Object>> getCountryRiskRank(Long diseaseId, String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> respList = new ArrayList<>();

        // ??????????????????????????????
        String dateTimeStr = DateUtil.getCurrentTime();
        if (!StringUtils.isEmpty(dateStr)) {
            dateTimeStr = dateStr + " 00:00:00";
        }
        Date yesStartDate;
        try {
            yesStartDate = sdf.parse(DateUtil.getYesterdayStart(dateTimeStr));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new DataintBaseException(BaseExceptionEnum.DATE_PARSE_ERROR);
        }

        List<Map<String, Object>> poList = diseaseCountryPODao.getRiskRankByDiseaseIdAndStatisticDate(diseaseId, yesStartDate,
                PageRequest.of(0, 5, Sort.by("riskScore").descending()));
        for (Map<String, Object> poMap : poList) {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("countryNameCn", poMap.get("0"));
            itemMap.put("riskScore", poMap.get("1"));
            respList.add(itemMap);
        }

        return respList;
    }

    @Override
    public List<Map<String, Object>> getEventAddTimeLine(Long diseaseId, String dateStr, Integer days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if (!StringUtils.isEmpty(dateStr)) {
            try {
                date = sdf.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new DataintBaseException(BaseExceptionEnum.DATE_PARSE_ERROR);
            }
        }
        List<Map<String, Object>> respList = buildRespList(date, days);

        List<IDayDate> dayList = statisticBasicDao.getDailyEventAddByDiseaseId(diseaseId, days);
        for (IDayDate item : dayList) {
            respList.forEach((e) -> {
                if (item.getDay().equals(e.get("day"))) {
                    e.put("value", item.getCnt());
                }
            });
        }

        return respList;
    }

    @Override
    public List<Map<String, Object>> getEventTotalCntRank(Long diseaseId, String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> respList = new ArrayList<>();

        // ??????????????????????????????
        String dateTimeStr = DateUtil.getCurrentTime();
        if (!StringUtils.isEmpty(dateStr)) {
            dateTimeStr = dateStr + " 00:00:00";
        }
        Date yesStartDate;
        try {
            yesStartDate = sdf.parse(DateUtil.getYesterdayStart(dateTimeStr));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new DataintBaseException(BaseExceptionEnum.DATE_PARSE_ERROR);
        }

        List<Map<String, Object>> poList = diseaseCountryPODao.getEventCntByDiseaseIdAndStatisticDate(diseaseId, yesStartDate,
                PageRequest.of(0, 5, Sort.by("eventTotal").descending()));
        for (Map<String, Object> poMap : poList) {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("countryNameCn", poMap.get("0"));
            if (poMap.get("1") != null) {
                itemMap.put("eventTotal", poMap.get("1"));
            } else {
                itemMap.put("eventTotal", 0);
            }
            respList.add(itemMap);
        }

        return respList;
    }

    @Override
    public List<Map<String, Object>> getArticleAddTimeLine(Long diseaseId, String dateStr, Integer days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if (!StringUtils.isEmpty(dateStr)) {
            try {
                date = sdf.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new DataintBaseException(BaseExceptionEnum.DATE_PARSE_ERROR);
            }
        }
        List<Map<String, Object>> respList = buildRespList(date, days);

        List<IDayDate> dayList = statisticBasicDao.getDailyArticleAddByDiseaseId(diseaseId, days);
        for (IDayDate item : dayList) {
            respList.forEach((e) -> {
                if (item.getDay().equals(e.get("day"))) {
                    e.put("value", item.getCnt());
                }
            });
        }

        return respList;
    }

    @Override
    public List<Map<String, Object>> getArticleTotalCntRank(Long diseaseId, String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> respList = new ArrayList<>();

        // ??????????????????????????????
        String dateTimeStr = DateUtil.getCurrentTime();
        if (!StringUtils.isEmpty(dateStr)) {
            dateTimeStr = dateStr + " 00:00:00";
        }
        Date yesStartDate;
        try {
            yesStartDate = sdf.parse(DateUtil.getYesterdayStart(dateTimeStr));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new DataintBaseException(BaseExceptionEnum.DATE_PARSE_ERROR);
        }

        List<Map<String, Object>> poList = diseaseCountryPODao.getArticleCntByDiseaseIdAndStatisticDate(diseaseId, yesStartDate,
                PageRequest.of(0, 5, Sort.by("articleTotal").descending()));
        for (Map<String, Object> poMap : poList) {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("countryNameCn", poMap.get("0"));
            if (poMap.get("1") != null) {
                itemMap.put("articleTotal", poMap.get("1"));
            } else {
                itemMap.put("articleTotal", 0);
            }
            respList.add(itemMap);
        }

        return respList;
    }

    @Override
    public List<Map<String, Object>> getArticleAddTimeLineByType(Long diseaseId, String dateStr, Integer days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if (!StringUtils.isEmpty(dateStr)) {
            try {
                date = sdf.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new DataintBaseException(BaseExceptionEnum.DATE_PARSE_ERROR);
            }
        }

        // ?????????????????????
        List<Map<String, Object>> respList = buildRespList(date, days);
        respList.forEach(map -> {
            map.put("officialAdd", 0);
            map.put("mediaAdd", 0);
        });

        // ??????????????????
        List<Map<String, Object>> dayList = diseaseCountryPODao.getArticleAddGroupByDiseaseIdAndStatisticDate(diseaseId,dateStr, days);

        // ???????????????
        for (Map<String,Object> item : dayList) {
            respList.forEach((e) -> {
                if (sdf.format(item.get("statisticDate")).equals(e.get("day"))) {
                    Object officialAdd = item.get("officialAdd");
                    Object mediaAdd = item.get("mediaAdd");

                    if (officialAdd != null) {
                        e.put("officialAdd", Integer.valueOf((String) officialAdd));
                    }
                    if (mediaAdd != null) {
                        e.put("mediaAdd", Integer.valueOf((String) mediaAdd));
                    }
                    e.put("value", (int) e.get("officialAdd") + (int) e.get("mediaAdd"));
                }
            });
        }

        return respList;
    }

    @Override
    public List<Map<String, Object>> getMapCountryList() {
        List<Map<String, Object>> respList = new ArrayList<>();

        List<IMapCountry> mapCountryList = diseaseCountryCaseDao.getMapCountryList();
        mapCountryList.forEach((e) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("countryId", e.getCountryId());
            map.put("countryNameCn", e.getCountryNameCn());

            respList.add(map);
        });

        return respList;
    }

    @Override
    public List<Map<String, Object>> getGlobalRiskTimeLine(String dateStr, Integer days) {
        Date date = new Date();
        if (!StringUtils.isEmpty(dateStr)) {
            try {
                date = Constants.getDateFormat().parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new DataintBaseException(BaseExceptionEnum.DATE_PARSE_ERROR);
            }
        }
        List<Map<String, Object>> respList = buildRespList(date, days);

        List<Map<String, Object>> dayList = statisticGlobalRiskDao.getIntervalGlobalRisks(days);
        for (Map<String, Object> item : dayList) {
            respList.forEach((e) -> {
                if ((item.get("statisticDate")).equals(e.get("day"))) {
                    e.put("value", item.get("globalRiskScore"));
                }
            });
        }

        return respList;
    }

    @Override
    public List<Map<String, Object>> getDeathTimeLine(Long diseaseId, String dateStr, Integer days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if (!StringUtils.isEmpty(dateStr)) {
            try {
                date = sdf.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new DataintBaseException(BaseExceptionEnum.DATE_PARSE_ERROR);
            }
        }
        ArrayList<String> countryNameList = new ArrayList<>();

        List<DiseaseCountryCase> dayList = diseaseCountryCaseDao.getDailyDeathAddByDiseaseId(diseaseId, 5, dateStr, days);
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        List<Map<String, Object>> respList = new ArrayList<>();
        for (DiseaseCountryCase item : dayList) {
            if (!countryNameList.contains(item.getCountryNameCn())) {
                if (countryNameList.size() > 0) {
                    map.put("data", respList);
                    HashMap<String, Object> cloneMap = new HashMap<>(map);
                    maps.add(cloneMap);
                    map.clear();
                }
                respList = buildRespList(date, days);
                countryNameList.add(item.getCountryNameCn());
                map.put("countryName", item.getCountryNameCn());
            }
            respList.forEach((e) -> {
//                System.out.println(sdf.format(item.getStatisticDate()));
                if (sdf.format(item.getStatisticDate()).equals(e.get("day"))) {
                    e.put("value", item.getDeathAdd());
                }
            });
        }
        if (countryNameList.size() > 0) {
            map.put("data", respList);
            HashMap<String, Object> cloneMap = new HashMap<>(map);
            maps.add(cloneMap);
            map.clear();
        }
        return maps;
    }

    @Override
    public List<Map<String, Object>> getDeathTotalCntRank(Long diseaseId, String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> respList = new ArrayList<>();

        // ??????????????????????????????
        String dateTimeStr = DateUtil.getCurrentTime();
        if (!StringUtils.isEmpty(dateStr)) {
            dateTimeStr = dateStr + " 00:00:00";
        }
        Date yesStartDate;
        try {
            yesStartDate = sdf.parse(DateUtil.getYesterdayStart(dateTimeStr));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new DataintBaseException(BaseExceptionEnum.DATE_PARSE_ERROR);
        }

        List<Map<String, Object>> poList = diseaseCountryCaseDao.getDeathCntByDiseaseIdAndStatisticDate(diseaseId, yesStartDate,
                PageRequest.of(0, 5)).getContent();
        for (Map<String, Object> poMap : poList) {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("countryNameCn", poMap.get("0"));

            Object rate = poMap.get("1");
            if (rate == null || "null".equals(rate)) {
                itemMap.put("deathPercent", 0);
            } else {
                itemMap.put("deathPercent", Double.valueOf((String) rate)/ 100);
            }

            respList.add(itemMap);
        }

        return respList;
    }

    @Override
    public List<Map<String, Object>> getConfirmedTimeLine(Long diseaseId, String dateStr, Integer days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if (!StringUtils.isEmpty(dateStr)) {
            try {
                date = sdf.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new DataintBaseException(BaseExceptionEnum.DATE_PARSE_ERROR);
            }
        }

        ArrayList<String> countryNameList = new ArrayList<>();

        List<DiseaseCountryCase> dayList = diseaseCountryCaseDao.getDailyConFirmedAddByDiseaseId(diseaseId, 5, dateStr, days);
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        List<Map<String, Object>> respList = new ArrayList<>();
        for (DiseaseCountryCase item : dayList) {
            if (!countryNameList.contains(item.getCountryNameCn())) {
                if (countryNameList.size() > 0) {
                    map.put("data", respList);
                    HashMap<String, Object> cloneMap = new HashMap<>(map);
                    maps.add(cloneMap);
                    map.clear();
                }
                respList = buildRespList(date, days);
                countryNameList.add(item.getCountryNameCn());
                map.put("countryName", item.getCountryNameCn());
            }
            respList.forEach((e) -> {
//                System.out.println(sdf.format(item.getStatisticDate()));
                if (sdf.format(item.getStatisticDate()).equals(e.get("day"))) {
                    e.put("value", item.getConfirmAdd());
                }
            });
        }
        if (countryNameList.size() > 0) {
            map.put("data", respList);
            HashMap<String, Object> cloneMap = new HashMap<>(map);
            maps.add(cloneMap);
            map.clear();
        }
        return maps;
    }

    @Override
    public List<Map<String, Object>> getConfirmedTotalCntRank(Long diseaseId, String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> respList = new ArrayList<>();

        // ??????????????????????????????
        String dateTimeStr = DateUtil.getCurrentTime();
        if (!StringUtils.isEmpty(dateStr)) {
            dateTimeStr = dateStr + " 00:00:00";
        }
        Date yesStartDate;
        try {
            yesStartDate = sdf.parse(DateUtil.getYesterdayStart(dateTimeStr));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new DataintBaseException(BaseExceptionEnum.DATE_PARSE_ERROR);
        }

        List<Map<String, Object>> poList = diseaseCountryCaseDao.getConfirmedCntByDiseaseIdAndStatisticDate(diseaseId, yesStartDate,
                PageRequest.of(0, 5));
        for (Map<String, Object> poMap : poList) {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("countryNameCn", poMap.get("0"));
            itemMap.put("confirmedCase", poMap.get("1"));
            respList.add(itemMap);
        }

        return respList;
    }

    @Override
    public List<Map<String, Object>> getCuredTimeLine(Long diseaseId, String dateStr, Integer days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if (!StringUtils.isEmpty(dateStr)) {
            try {
                date = sdf.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new DataintBaseException(BaseExceptionEnum.DATE_PARSE_ERROR);
            }
        }
        ArrayList<String> countryNameList = new ArrayList<>();

        List<DiseaseCountryCase> dayList = diseaseCountryCaseDao.getDailyCuredAddByDiseaseId(diseaseId, 5, dateStr, days);
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        List<Map<String, Object>> respList = new ArrayList<>();
        for (DiseaseCountryCase item : dayList) {
            if (!countryNameList.contains(item.getCountryNameCn())) {
                if (countryNameList.size() > 0) {
                    map.put("data", respList);
                    HashMap<String, Object> cloneMap = new HashMap<>(map);
                    maps.add(cloneMap);
                    map.clear();
                }
                respList = buildRespList(date, days);
                countryNameList.add(item.getCountryNameCn());
                map.put("countryName", item.getCountryNameCn());
            }
            respList.forEach((e) -> {
//                System.out.println(sdf.format(item.getStatisticDate()));
                if (sdf.format(item.getStatisticDate()).equals(e.get("day"))) {
                    e.put("value", item.getCureAdd());
                }
            });
        }
        if (countryNameList.size() > 0) {
            map.put("data", respList);
            HashMap<String, Object> cloneMap = new HashMap<>(map);
            maps.add(cloneMap);
            map.clear();
        }
        return maps;
    }

    @Override
    public List<Map<String, Object>> getCuredTotalCntRank(Long diseaseId, String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> respList = new ArrayList<>();

        // ??????????????????????????????
        String dateTimeStr = DateUtil.getCurrentTime();
        if (!StringUtils.isEmpty(dateStr)) {
            dateTimeStr = dateStr + " 00:00:00";
        }
        Date yesStartDate;
        try {
            yesStartDate = sdf.parse(DateUtil.getYesterdayStart(dateTimeStr));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new DataintBaseException(BaseExceptionEnum.DATE_PARSE_ERROR);
        }

        List<Map<String, Object>> poList = diseaseCountryCaseDao.getCuredCntByDiseaseIdAndStatisticDate(diseaseId, yesStartDate,
                PageRequest.of(0, 5));
        for (Map<String, Object> poMap : poList) {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("countryNameCn", poMap.get("0"));

            Object rate = poMap.get("1");
            if (rate == null || "null".equals(rate)) {
                itemMap.put("curedPercent", 0);
            } else {
                itemMap.put("curedPercent", Double.valueOf((String) rate)/ 100);
            }
            respList.add(itemMap);
        }

        return respList;
    }

    /**
     * ???????????????????????????
     * @param date
     * @param days
     * @return
     */
    private static List<Map<String, Object>> buildRespList(Date date, Integer days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> respList = new ArrayList<>(days);
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(Calendar.DAY_OF_YEAR, -days);
        for (int i = days; i > 0; i--) {
            Map<String, Object> dataMap = new HashMap<>(2);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            dataMap.put("day", sdf.format(calendar.getTime()));
            dataMap.put("value", 0);
            respList.add(dataMap);
        }

        return respList;
    }
}
