package com.dataint.service.datapack.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.exception.DataNotExistException;
import com.dataint.cloud.common.model.Constants;
import com.dataint.service.datapack.dao.IArticleDao;
import com.dataint.service.datapack.dao.IArticleDiseaseDao;
import com.dataint.service.datapack.dao.ICountryDao;
import com.dataint.service.datapack.dao.entity.Country;
import com.dataint.service.datapack.service.IStatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StatisticServiceImpl implements IStatisticService {

    @Autowired
    private IArticleDao articleDao;

    @Autowired
    private ICountryDao countryDao;

    @Autowired
    private IArticleDiseaseDao articleDiseaseDao;

    @Override
    public JSONObject getPeriodBasic(String startTime, String endTime) {
        JSONObject periodJO = new JSONObject();

        Date startTimeDate, endTimeDate;
        try {
            startTimeDate = Constants.DateTimeSDF.parse(startTime);
            endTimeDate = Constants.DateTimeSDF.parse(endTime);
        } catch (ParseException e) {
            log.error("时间格式错误!");
            e.printStackTrace();
            return periodJO;
        }

        // 指定时间内舆情数量
        int articleCnt = articleDao.countByGmtCrawlBetween(startTimeDate, endTimeDate);

        // 指定时间内疫情类别数
        List<Object[]> diseaseCntList = articleDao.queryDiseaseCnt(startTimeDate, endTimeDate);
        int diseaseCnt = diseaseCntList.size();

        // 指定时间内涉及国家地区数量()
        Set<String> countrySet = new HashSet<>();
        Set<String> aCountries = articleDao.findCountryCodeFromA(startTimeDate, endTimeDate);  // Article
        Set<String> adCountries = articleDao.findCountryCodesFromAD(startTimeDate, endTimeDate);  // ArticleDisease
        aCountries.addAll(adCountries);
        for (String str : aCountries) {
            countrySet.addAll(Arrays.stream(str.split(Constants.SPLITTER)).collect(Collectors.toSet()));
        }
        int countryCnt = countrySet.size();

        /*  */
        periodJO.put("articleCnt", articleCnt);
        periodJO.put("diseaseCnt", diseaseCnt);
        periodJO.put("countryCnt", countryCnt);
        return periodJO;
    }

    @Override
    public long getArticleTotal() {

        return articleDao.count();
    }

    @Override
    public JSONObject getMapInfoByCountry(String startTime, String endTime, Integer countryId) {
        JSONObject mapInfoJO = new JSONObject();

        Date startTimeDate, endTimeDate;
        try {
            startTimeDate = Constants.DateTimeSDF.parse(startTime);
            endTimeDate = Constants.DateTimeSDF.parse(endTime);
        } catch (ParseException e) {
            log.error("时间格式错误!");
            e.printStackTrace();
            return mapInfoJO;
        }

        // 检查国家信息是否存在
        Optional<Country> countryOpt = countryDao.findById(countryId);
        if (!countryOpt.isPresent()) {
            throw new DataNotExistException();
        }
        Country country = countryOpt.get();

        // 统计指定国家在指定时间段内 - 舆情(Article.id)数量
        Set<Integer> articleIdSet = articleDao.queryArticleIdsByCountry(startTimeDate, endTimeDate, country.getCode());
        int articleCnt = articleIdSet.size();

        // 统计指定国家在指定时间段内 - 疫情(ArticleDisease.diseaseCode)及数量
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
        jo.put("articleYearlyCnt", articleCnt);
        jo.put("diseaseCntJA", diseaseCntJA);

        return jo;
    }
}
