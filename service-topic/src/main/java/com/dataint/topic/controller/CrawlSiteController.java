package com.dataint.topic.controller;

import com.dataint.topic.model.CrawlSiteVO;
import com.dataint.topic.model.UpdateCrawlSiteReq;
import com.dataint.topic.service.ICrawlSiteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/crawlsite")
public class CrawlSiteController {
    @Autowired
    private ICrawlSiteService crawlSiteService;

    @GetMapping("/getCrawlSiteList")
    @ResponseBody
    public Object getCrawlSiteList() {

        return crawlSiteService.getCrawlSiteList();
    }

    @GetMapping("/getCrawlSiteNames")
    @ResponseBody
    public Object getCrawlSiteNames() {

        return crawlSiteService.getCrawlSiteNames();
    }

    @GetMapping("/getCrawlSiteById/{siteId}")
    @ResponseBody
    public CrawlSiteVO getCrawlSiteById(@PathVariable("siteId") Integer siteId) {

        return crawlSiteService.getCrawlSiteById(siteId);
    }

    @PostMapping("/add")
    @ResponseBody
    public Object addCrawlSite(@RequestBody CrawlSiteVO crawlSiteVO) {

        return crawlSiteService.addCrawlSite(crawlSiteVO);
    }

    @PostMapping("/update")
    @ResponseBody
    public Object updateCrawlSiteById(@RequestBody UpdateCrawlSiteReq updateCrawlSiteReq) {

        return crawlSiteService.updateCrawlSiteById(updateCrawlSiteReq.getSiteId(), updateCrawlSiteReq.getCrawlSiteVO());
    }

    @GetMapping("/delete/{siteId}")
    @ResponseBody
    public Object deleteCrawlSiteById(@PathVariable("siteId") Integer siteId) {

        return crawlSiteService.deleteCrawlSiteById(siteId);
    }
}
