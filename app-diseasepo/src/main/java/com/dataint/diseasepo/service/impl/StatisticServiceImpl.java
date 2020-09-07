package com.dataint.diseasepo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.cloud.common.utils.DateUtil;
import com.dataint.diseasepo.dao.*;
import com.dataint.diseasepo.dao.entity.*;
import com.dataint.diseasepo.model.*;
import com.dataint.diseasepo.provider.ArticleProvider;
import com.dataint.diseasepo.provider.StatisticProvider;
import com.dataint.diseasepo.service.IStatisticService;
import com.dataint.diseasepo.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticServiceImpl implements IStatisticService {

    @Autowired
    private IMonthlyTimeDao monthlyTimeDao;

    @Autowired
    private IMonthlyCountryDao monthlyCountryDao;

    @Autowired
    private IImpactRankDao impactRankDao;

    @Autowired
    private IStatisticBasicDao statisticBasicDao;

    @Autowired
    private StatisticProvider statisticProvider;

    @Autowired
    private IStatisticMapDao statisticMapDao;

    @Autowired
    private IFocusCountryDao focusCountryDao;

    @Autowired
    private ArticleProvider articleProvider;

    @Override
    public BasicInfoVO getBasicInfo() {
        StatisticBasic ifExist = statisticBasicDao.findFirstByOrderByStatisticStartDesc();
        if (ifExist == null) {
            // mock data
            ifExist = new StatisticBasic();
            ifExist.setDiseaseTotalCnt(32520);
            ifExist.setTypeYearlyCnt(123);
            ifExist.setCountryYearlyCnt(231);
            ifExist.setCaseYearlyCnt(45653);
            ifExist.setDeathYearlyCnt(1113);
            ifExist.setDiseaseDailyCnt(5);
            ifExist.setTypeDailyCnt(3);
            ifExist.setCountryDailyCnt(12);
        } else {
            // 获取今天起始结束时间
            String todayStart = DateUtil.getTodayStart();
            String todayEnd = DateUtil.getTodayEnd();

            JSONObject retJO = statisticProvider.getPeriodBasic(todayStart, todayEnd).getData();
            if (retJO != null) {
                ifExist.setDiseaseDailyCnt(retJO.getInteger("articleCnt"));
                ifExist.setTypeDailyCnt(retJO.getInteger("diseaseCnt"));
                ifExist.setCountryDailyCnt(retJO.getInteger("countryCnt"));
            }
        }

        return new BasicInfoVO(ifExist);
    }

    @Override
    public List<BIMonthlyByTimeVO> getMonthlyByTime() {
        List<BIMonthlyByTimeVO> monthlyByTimeVOList = new ArrayList<>();

        // mock data
        for (String month : Constants.monthArray) {
            // 读取当前月份最新统计数据
            List<MonthlyDataVO> monthlyDataVOList = new ArrayList<>();
            List<MonthlyTime> monthlyTimeList = monthlyTimeDao.findByMonthAndLatestOrderByDiseaseSort(month, "Y");
            for (MonthlyTime monthlyTime : monthlyTimeList) {
                MonthlyDataVO monthlyDataVO = new MonthlyDataVO();
                monthlyDataVO.setDiseaseId(monthlyTime.getDiseaseId());  // 传染病表主键Id
                monthlyDataVO.setDiseaseName(monthlyTime.getDiseaseName());  // 传染病名称
                monthlyDataVO.setCountryNames(monthlyTime.getCountryNames());  // 主要波及国家名称(英文符号逗号分隔)

                monthlyDataVOList.add(monthlyDataVO);
            }

            // 构建月统计(按时间)数据格式
            BIMonthlyByTimeVO biMonthlyByTimeVO = new BIMonthlyByTimeVO();
            biMonthlyByTimeVO.setMonth(month);
            biMonthlyByTimeVO.setDataList(monthlyDataVOList);

            monthlyByTimeVOList.add(biMonthlyByTimeVO);
        }

        return monthlyByTimeVOList;
    }

    @Override
    public List<BIMonthlyByCountryVO> getMonthlyByCountry() {
        List<BIMonthlyByCountryVO> monthlyByCountryVOList = new ArrayList<>();

        // 获取数据(需保证按国家分组且各国家传染病顺序)
        List<MonthlyCountry> monthlyCountryList = monthlyCountryDao.findMonthlyCountryList("Y");

        //
        List<MonthlyDataVO> dataVOList = new ArrayList<>();
        BIMonthlyByCountryVO biMonthlyByCountryVO = new BIMonthlyByCountryVO();
        for (MonthlyCountry monthlyCountry : monthlyCountryList) {
            if (StringUtils.isEmpty(biMonthlyByCountryVO.getCountryName())) {
                biMonthlyByCountryVO.setCountryName(monthlyCountry.getCountryName());
            }

            MonthlyDataVO monthlyDataVO = new MonthlyDataVO();
            monthlyDataVO.setDiseaseId(monthlyCountry.getDiseaseId());
            monthlyDataVO.setDiseaseName(monthlyCountry.getDiseaseName());
            monthlyDataVO.setMonths(monthlyCountry.getMonths());

            // 对于前n-1个国家, 当出现国家名称不一致时, 第n-1个国家统计数据(dataVOList)已加载完毕, 构造BIMonthlyByCountryVO
            if (!monthlyCountry.getCountryName().equals(biMonthlyByCountryVO.getCountryName())) {
                biMonthlyByCountryVO.setDataList(dataVOList);
                monthlyByCountryVOList.add(biMonthlyByCountryVO);

                // 初始化第n个国家名称, 及对应数据列表
                biMonthlyByCountryVO = new BIMonthlyByCountryVO();
                biMonthlyByCountryVO.setCountryName(monthlyCountry.getCountryName());
                dataVOList = new ArrayList<>();
            }
            dataVOList.add(monthlyDataVO);
        }

        // 构造最后一个国家BIMonthlyByCountryVO
        biMonthlyByCountryVO.setDataList(dataVOList);
        monthlyByCountryVOList.add(biMonthlyByCountryVO);

        return monthlyByCountryVOList;
    }

    @Override
    public List<ImpactRankVO> getImpactRank() {
        List<ImpactRankVO> impactRankVOList = new ArrayList<>();

        //
        List<ImpactRank> impactRankList = impactRankDao.findByLatestOrderByRank("Y");
        if (impactRankList != null) {
            impactRankVOList = impactRankList.stream().map(ImpactRankVO::new).collect(Collectors.toList());
        }

        return impactRankVOList;
    }

    @Override
    public List<MapInfoVO> getMapInfo() {
        List<MapInfoVO> mapInfoVOList = new ArrayList<>();

        List<MapBasic> mapBasicList = statisticMapDao.findByLatestOrderByDiseaseYearlyCnt("Y");
        if (!CollectionUtils.isEmpty(mapBasicList)) {
            mapInfoVOList = mapBasicList.stream().map(MapInfoVO::new).collect(Collectors.toList());
        }

        return mapInfoVOList;
    }

    @Override
    public MapDetailVO getMapDetail(Integer countryId, String diseaseName, PageParam pageParam) {
        MapDetailVO mapDetailVO = new MapDetailVO();
        mapDetailVO.setDiseaseName(diseaseName);

        ResultVO<JSONArray> resultVO = articleProvider.queryMapBasicList(countryId, diseaseName, pageParam.getCurrent(), pageParam.getPageSize());
        if (!ObjectUtils.isEmpty(resultVO)) {
            mapDetailVO.setArticleList(resultVO.getData());
        }

        // TODO: 病例数 死亡数
        mapDetailVO.setCaseCnt(0);
        mapDetailVO.setDeathCnt(0);

        return mapDetailVO;
    }

    @Override
    public void dailyStatistic(String dailyStartTime, String dailyEndTime, String year1AgoStartTime) {
        Date dailyStartTimeDate, dailyEndTimeDate;
        try {
            dailyStartTimeDate = com.dataint.cloud.common.model.Constants.DateTimeSDF.parse(dailyStartTime);
            dailyEndTimeDate = com.dataint.cloud.common.model.Constants.DateTimeSDF.parse(dailyEndTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        /*
         * Basic Counts
         */
        StatisticBasic basic = new StatisticBasic();
        // 前一天基础数据统计(舆情数, 涉及疫情数, 涉及国家(地区)数)
        JSONObject dailyBasicJO = statisticProvider.getPeriodBasic(dailyStartTime, dailyEndTime).getData();
        if (dailyBasicJO != null) {
            basic.setDiseaseDailyCnt(dailyBasicJO.getInteger("articleCnt"));
            basic.setTypeDailyCnt(dailyBasicJO.getInteger("diseaseCnt"));
            basic.setCountryDailyCnt(dailyBasicJO.getInteger("countryCnt"));
        }
        // 今年(昨天的一年前)基础数据统计(涉及疫情数, 涉及国家(地区)数)
        JSONObject yearlyBasicJO = statisticProvider.getPeriodBasic(year1AgoStartTime, dailyEndTime).getData();
        if (dailyBasicJO != null) {
            basic.setTypeYearlyCnt(yearlyBasicJO.getInteger("diseaseCnt"));
            basic.setCountryYearlyCnt(yearlyBasicJO.getInteger("countryCnt"));
        }
        // 舆情总数统计
        Integer totalCnt = statisticProvider.getArticleTotal().getData();
        basic.setDiseaseTotalCnt(totalCnt);

        // TODO: 今年(昨天的一年前)病例个数, 死亡人数
        basic.setCaseYearlyCnt(274256);
        basic.setDeathYearlyCnt(21465);

        // 赋值其他信息
        basic.setStatisticStart(dailyStartTimeDate);
        basic.setStatisticEnd(dailyEndTimeDate);
        basic.setCreatedTime(new Date());
        // 持久化
        StatisticBasic ifExist = statisticBasicDao.findByStatisticStart(dailyStartTimeDate);
        if (ifExist != null)
            basic.setId(ifExist.getId());
        statisticBasicDao.save(basic);

        /*
         * Map Counts
         */
        List<MapBasic> mapBasicList = new ArrayList<>();
        //
        List<FocusCountry> focusCountryList = focusCountryDao.findAll();
        for (FocusCountry focusCountry : focusCountryList) {
            JSONObject mapInfoJO = statisticProvider.getMapInfoByCountry(year1AgoStartTime, dailyStartTime, focusCountry.getCountryId()).getData();
            if (mapInfoJO != null) {
                MapBasic mapBasic = new MapBasic();
                mapBasic.setCountryId(focusCountry.getCountryId());
                mapBasic.setCountryCode(mapInfoJO.getString("countryCode"));
                mapBasic.setCountryName(mapInfoJO.getString("countryName"));
                mapBasic.setDiseaseYearlyCnt(mapInfoJO.getInteger("articleYearlyCnt"));
                // statisticMapDetails
                if (!ObjectUtils.isEmpty(mapInfoJO.getJSONArray("diseaseCntJA"))) {
                    List<MapDisease> detailList = mapInfoJO.getJSONArray("diseaseCntJA").stream().map(object-> {
                        Map detailMap = (Map)object;
                        MapDisease mapDisease = new MapDisease();
                        mapDisease.setDiseaseName((String) detailMap.get("diseaseName"));
                        mapDisease.setDiseaseCnt((Integer) detailMap.get("diseaseCnt"));
                        // TODO: 国家对应疫情病例数死亡数统计

                        return mapDisease;
                    }).collect(Collectors.toList());
                    mapBasic.setDetailList(detailList);
                }
                mapBasic.setCreatedTime(new Date());
                mapBasic.setLatest("Y");

                mapBasicList.add(mapBasic);
            }
        }
        // 持久化
        statisticMapDao.updateLatest();
        statisticMapDao.saveAll(mapBasicList);
    }
}
