package com.dataint.topic.model.form;

import com.dataint.topic.model.vo.CrawlSiteVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCrawlSiteForm {

    Integer siteId;

    CrawlSiteVO crawlSiteVO;
}
