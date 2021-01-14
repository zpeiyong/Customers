package com.dataint.monitor.model;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
public class ReportBaseModel {

    private String reportType;

    private Integer serialNo;

    private Integer totalNo;

    private String reportDate;

    private LinkedHashMap<String, List<ArticleReportVO>> listMap;

//    private List<ArticleReportVO> concernList;
//
//    private List<ArticleReportVO> moreInfoList;
//
//    private List<ReportRecall> recallList;

}
