package com.dataint.monitor.model;

import lombok.Data;

import java.util.List;

@Data
public class ReportBaseModel {

    private String reportType;

    private Integer serialNo;

    private Integer totalNo;

    private String reportDate;

    private List<ReportArticle> concernList;

    private List<ReportArticle> moreInfoList;

//    private List<ReportRecall> recallList;

}
