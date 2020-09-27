package com.dataint.topic.service;

import com.dataint.topic.db.entity.CrawlSite;
import com.dataint.topic.model.vo.CrawlSiteVO;

import java.util.List;

public interface ICrawlSiteService {

    Object getCrawlSiteList();

    Object getCrawlSiteNames();

    CrawlSiteVO getCrawlSiteById(Integer siteId);

    Object addCrawlSite(CrawlSiteVO crawlSiteVO);

    Object updateCrawlSiteById(Integer siteId, CrawlSiteVO crawlSiteVO);

    Object deleteCrawlSiteById(Integer siteId);

    List<CrawlSite> getEnableCrawlSiteList();
}
