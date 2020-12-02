package com.dataint.monitor.model.form;

import com.dataint.monitor.model.CrawlSiteVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCrawlSiteForm {

    Integer siteId;

    CrawlSiteVO crawlSiteVO;
}
