package com.dataint.topic.service.impl;

import com.dataint.topic.db.entity.CrawlSite;
import com.dataint.topic.db.dao.ICrawlSiteDao;
import com.dataint.topic.model.vo.CrawlSiteVO;
import com.dataint.topic.service.ICrawlSiteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrawlSiteServiceImpl implements ICrawlSiteService {
    @Autowired
    private ICrawlSiteDao crawlSiteDao;

    @Override
    public Object getCrawlSiteList() {
        return null;
    }

    @Override
    public Object getCrawlSiteNames() {
        List<Map<String, Object>> retList = new ArrayList<>();

        List<Map> siteNameList = crawlSiteDao.getCrawlSiteNames(true);

        for (Map siteNameMap : siteNameList) {
            Map<String, Object> map = new HashMap<>();
            map.put("siteId", siteNameMap.get("0"));
            map.put("siteName", siteNameMap.get("1"));

            retList.add(map);
        }

        return retList;
    }

    @Override
    public CrawlSiteVO getCrawlSiteById(Integer siteId) {
        return null;
    }

    @Override
    public Object addCrawlSite(CrawlSiteVO crawlSiteVO) {
        return null;
    }

    @Override
    public Object updateCrawlSiteById(Integer siteId, CrawlSiteVO crawlSiteVO) {
        return null;
    }

    @Override
    public Object deleteCrawlSiteById(Integer siteId) {
        return null;
    }

    @Override
    public List<CrawlSite> getEnableCrawlSiteList() {

        return crawlSiteDao.findAllByEnable("1");
    }
}
