package com.dataint.topic.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCrawlSiteReq {

    Integer siteId;

    CrawlSiteVO crawlSiteVO;
}
