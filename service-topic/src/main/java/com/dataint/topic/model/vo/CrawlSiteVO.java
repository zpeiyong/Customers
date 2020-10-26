package com.dataint.topic.model.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrawlSiteVO {

    private Long siteId;

    private String siteName;

    private String projectName;  // default project name

    private String script;
}
