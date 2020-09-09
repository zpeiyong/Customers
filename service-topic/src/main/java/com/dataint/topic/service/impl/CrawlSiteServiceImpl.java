package com.dataint.topic.service.impl;

import com.dataint.topic.db.entity.CrawlSite;
import com.dataint.topic.db.repository.CrawlSiteRepository;
import com.dataint.topic.model.CrawlSiteVO;
import com.dataint.topic.service.ICrawlSiteService;
import com.dataint.topic.common.exception.ThinventBaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrawlSiteServiceImpl implements ICrawlSiteService {
    @Autowired
    private CrawlSiteRepository crawlSiteRepository;

    @Override
    public Object getCrawlSiteList() throws ThinventBaseException {
        return null;
    }

    @Override
    public Object getCrawlSiteNames() throws ThinventBaseException {
        List<Map<String, Object>> retList = new ArrayList<>();

        List<Map> siteNameList = crawlSiteRepository.getCrawlSiteNames("1");

        for (Map siteNameMap : siteNameList) {
            Map<String, Object> map = new HashMap<>();
            map.put("siteId", siteNameMap.get("0"));
            map.put("siteName", siteNameMap.get("1"));

            retList.add(map);
        }

        return retList;
    }

    @Override
    public CrawlSiteVO getCrawlSiteById(Integer siteId) throws ThinventBaseException {
        return null;
    }

    @Override
    public Object addCrawlSite(CrawlSiteVO crawlSiteVO) throws ThinventBaseException {
        return null;
    }

    @Override
    public Object updateCrawlSiteById(Integer siteId, CrawlSiteVO crawlSiteVO) throws ThinventBaseException {
        return null;
    }

    @Override
    public Object deleteCrawlSiteById(Integer siteId) throws ThinventBaseException {
        return null;
    }

    @Override
    public List<CrawlSite> getEnableCrawlSiteList() {

        return crawlSiteRepository.findAllByEnable("1");
    }
}
