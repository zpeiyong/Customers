package com.dataint.monitor.service.impl;

import com.dataint.monitor.dao.IReportArticleDao;
import com.dataint.monitor.dao.entity.ReportArticle;
import com.dataint.monitor.model.form.ReportArticleForm;
import com.dataint.monitor.service.IReportArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportArticleServiceImpl implements IReportArticleService {

    @Autowired
    private IReportArticleDao reportArticleDao;

    @Override
    public void addReportArticle(ReportArticleForm reportArticleForm) {
        Date currentDate = new Date();

        for (Long articleId : reportArticleForm.getArticleIdList()) {
            // 维护已存在的关系列表
            List<ReportArticle> raList = reportArticleDao.findAllByArticleId(articleId);
            Map<String, ReportArticle> raMap = new HashMap<>();
            raList.forEach(reportArticle -> raMap.put(reportArticle.getReportType(), reportArticle));

            // 需要新增或更新的关系列表
            List<ReportArticle> reportArticleList = new ArrayList<>();
            // daily report
            if (reportArticleForm.getDailyLevelId() != null) {
                ReportArticle dailyRA;
                if (raMap.containsKey("daily")) {
                    dailyRA = raMap.get("daily");
                    if (dailyRA.getReportLevelId() != reportArticleForm.getDailyLevelId()) {
                        dailyRA.setReportLevelId(reportArticleForm.getDailyLevelId());
                        dailyRA.setUpdatedTime(currentDate);
                    }
                    raMap.remove("daily");
                } else {
                    dailyRA = new ReportArticle(articleId, "daily", reportArticleForm.getDailyLevelId());
                    dailyRA.setCreatedTime(currentDate);
                    dailyRA.setUpdatedTime(currentDate);
                }
                reportArticleList.add(dailyRA);
            }
            // weekly report
            if (reportArticleForm.getWeeklyLevelId() != null) {
                ReportArticle weeklyRA;
                if (raMap.containsKey("weekly")) {
                    weeklyRA = raMap.get("weekly");
                    if (weeklyRA.getReportLevelId() != reportArticleForm.getWeeklyLevelId()) {
                        weeklyRA.setReportLevelId(reportArticleForm.getWeeklyLevelId());
                        weeklyRA.setUpdatedTime(currentDate);
                    }
                    raMap.remove("weekly");
                } else {
                    weeklyRA = new ReportArticle(articleId, "weekly", reportArticleForm.getWeeklyLevelId());
                    weeklyRA.setCreatedTime(currentDate);
                    weeklyRA.setUpdatedTime(currentDate);
                }
                reportArticleList.add(weeklyRA);
            }
            // monthly report
            if (reportArticleForm.getMonthlyLevelId() != null) {
                ReportArticle monthlyRA;
                if (raMap.containsKey("monthly")) {
                    monthlyRA = raMap.get("monthly");
                    if (monthlyRA.getReportLevelId() != reportArticleForm.getMonthlyLevelId()) {
                        monthlyRA.setReportLevelId(reportArticleForm.getMonthlyLevelId());
                        monthlyRA.setUpdatedTime(currentDate);
                    }
                    raMap.remove("monthly");
                } else {
                    monthlyRA = new ReportArticle(articleId, "monthly", reportArticleForm.getMonthlyLevelId());
                    monthlyRA.setCreatedTime(currentDate);
                    monthlyRA.setUpdatedTime(currentDate);
                }
                reportArticleList.add(monthlyRA);
            }
            //  event report
            if (reportArticleForm.getEventLevelId() != null) {
                ReportArticle eventRA;
                if (raMap.containsKey("event")) {
                    eventRA = raMap.get("event");
                    if (eventRA.getReportLevelId() != reportArticleForm.getEventLevelId()) {
                        eventRA.setReportLevelId(reportArticleForm.getEventLevelId());
                        eventRA.setUpdatedTime(currentDate);
                    }
                    raMap.remove("event");
                } else {
                    eventRA = new ReportArticle(articleId, "event", reportArticleForm.getEventLevelId());
                    eventRA.setCreatedTime(currentDate);
                    eventRA.setUpdatedTime(currentDate);
                }
                reportArticleList.add(eventRA);
            }

            // 保存或更新
            reportArticleDao.saveAll(reportArticleList);

            // 删除未传入值的数据(raMap中留下的关系)
            reportArticleDao.deleteAll(raMap.values());
        }
    }
}
