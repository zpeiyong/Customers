package com.dataint.topic.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.topic.model.vo.CrawlSiteVO;
import com.dataint.topic.model.form.UpdateCrawlSiteForm;
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
    public ResultVO getCrawlSiteList() {

        return ResultVO.success(crawlSiteService.getCrawlSiteList());
    }

    @GetMapping("/getCrawlSiteNames")
    @ResponseBody
    public ResultVO getCrawlSiteNames() {

        return ResultVO.success(crawlSiteService.getCrawlSiteNames());
    }

    @GetMapping("/getCrawlSiteById/{siteId}")
    @ResponseBody
    public CrawlSiteVO getCrawlSiteById(@PathVariable("siteId") Integer siteId) {

        return crawlSiteService.getCrawlSiteById(siteId);
    }

    @PostMapping("/add")
    @ResponseBody
    public ResultVO addCrawlSite(@RequestBody CrawlSiteVO crawlSiteVO) {

        return ResultVO.success(crawlSiteService.addCrawlSite(crawlSiteVO));
    }

    @PostMapping("/update")
    @ResponseBody
    public ResultVO updateCrawlSiteById(@RequestBody UpdateCrawlSiteForm updateCrawlSiteReq) {

        return ResultVO.success(crawlSiteService.updateCrawlSiteById(updateCrawlSiteReq.getSiteId(), updateCrawlSiteReq.getCrawlSiteVO()));
    }

    @GetMapping("/delete/{siteId}")
    @ResponseBody
    public ResultVO deleteCrawlSiteById(@PathVariable("siteId") Integer siteId) {

        return ResultVO.success(crawlSiteService.deleteCrawlSiteById(siteId));
    }
}
