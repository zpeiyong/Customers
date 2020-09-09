package com.dataint.topic.controller;

import com.dataint.topic.model.CrawlSiteVO;
import com.dataint.topic.model.UpdateCrawlSiteReq;
import com.dataint.topic.service.ICrawlSiteService;
import com.dataint.topic.common.exception.ThinventBaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/crawlsite")
public class CrawlSiteController {
    @Autowired
    private ICrawlSiteService crawlSiteService;

    @GetMapping("/getCrawlSiteList")
    @ResponseBody
    public Object getCrawlSiteList() throws ThinventBaseException {

        return crawlSiteService.getCrawlSiteList();
    }

    @GetMapping("/getCrawlSiteNames")
    @ResponseBody
    public Object getCrawlSiteNames() throws ThinventBaseException {

        return crawlSiteService.getCrawlSiteNames();
    }

    @GetMapping("/getCrawlSiteById/{siteId}")
    @ResponseBody
    public CrawlSiteVO getCrawlSiteById(@PathVariable("siteId") Integer siteId) throws ThinventBaseException {

        return crawlSiteService.getCrawlSiteById(siteId);
    }

    @PostMapping("/add")
    @ResponseBody
    public Object addCrawlSite(@RequestBody CrawlSiteVO crawlSiteVO) throws ThinventBaseException {

        return crawlSiteService.addCrawlSite(crawlSiteVO);
    }

    @PostMapping("/update")
    @ResponseBody
    public Object updateCrawlSiteById(@RequestBody UpdateCrawlSiteReq updateCrawlSiteReq) throws ThinventBaseException {

        return crawlSiteService.updateCrawlSiteById(updateCrawlSiteReq.getSiteId(), updateCrawlSiteReq.getCrawlSiteVO());
    }

    @GetMapping("/delete/{siteId}")
    @ResponseBody
    public Object deleteCrawlSiteById(@PathVariable("siteId") Integer siteId) throws ThinventBaseException {

        return crawlSiteService.deleteCrawlSiteById(siteId);
    }
}
