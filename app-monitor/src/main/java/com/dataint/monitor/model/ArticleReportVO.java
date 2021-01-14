package com.dataint.monitor.model;

import lombok.Data;

@Data
public class ArticleReportVO {

    private Long id;

    private String title;

    private String summary;

    private String articleUrl;
}
