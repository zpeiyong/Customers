package com.dataint.topic.service;

import com.dataint.topic.db.entity.CrawlSite;
import com.dataint.topic.model.CrawlSiteVO;
import com.dataint.topic.common.exception.ThinventBaseException;

import java.util.List;

public interface ICrawlSiteService {

    Object getCrawlSiteList() throws ThinventBaseException;

    Object getCrawlSiteNames() throws ThinventBaseException;

    CrawlSiteVO getCrawlSiteById(Integer siteId) throws ThinventBaseException;

    Object addCrawlSite(CrawlSiteVO crawlSiteVO) throws ThinventBaseException;

    Object updateCrawlSiteById(Integer siteId, CrawlSiteVO crawlSiteVO) throws ThinventBaseException;

    Object deleteCrawlSiteById(Integer siteId) throws ThinventBaseException;

    List<CrawlSite> getEnableCrawlSiteList();
}
