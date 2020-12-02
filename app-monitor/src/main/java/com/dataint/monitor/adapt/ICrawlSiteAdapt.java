package com.dataint.monitor.adapt;


import com.dataint.monitor.model.CrawlSiteVO;

public interface ICrawlSiteAdapt {


    Object getCrawlSiteList();

    Object getCrawlSiteNames();

    Object getCrawlSiteById(Integer siteId);

    Object addCrawlSite(CrawlSiteVO crawlSiteVO);

    Object updateCrawlSiteById(Integer siteId, CrawlSiteVO crawlSiteVO);

    Object deleteCrawlSiteById(Integer siteId);
}
