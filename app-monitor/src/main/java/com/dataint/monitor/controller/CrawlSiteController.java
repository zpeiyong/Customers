package com.dataint.monitor.controller;

import com.dataint.monitor.adapt.ICrawlSiteAdapt;
import com.dataint.monitor.model.CrawlSiteVO;
import com.dataint.monitor.model.form.UpdateCrawlSiteForm;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/crawlsite")
public class CrawlSiteController {
    @Autowired
    private ICrawlSiteAdapt crawlSiteAdapt;

    @GetMapping("/getCrawlSiteList")
    @ResponseBody
    public Object getCrawlSiteList() {

        return crawlSiteAdapt.getCrawlSiteList();
    }

    /**
     * 查询所有信息来源
     *
     * @auther: Tim_Huo
     * @return: Object
     * @date: 2020/10/28 10:29 上午
     */
    @ApiOperation(value = "获取信息来源" , notes = "获取信息来源")
    @GetMapping("/getCrawlSiteNames")
    @ResponseBody
    public Object getCrawlSiteNames() {

        return crawlSiteAdapt.getCrawlSiteNames();
    }

    @GetMapping("/getCrawlSiteById/{siteId}")
    @ResponseBody
    public Object getCrawlSiteById(@PathVariable("siteId") Integer siteId) {

        return crawlSiteAdapt.getCrawlSiteById(siteId);
    }

    @PostMapping("/add")
    @ResponseBody
    public Object addCrawlSite(@RequestBody CrawlSiteVO crawlSiteVO) {

        return crawlSiteAdapt.addCrawlSite(crawlSiteVO);
    }

    @PostMapping("/update")
    @ResponseBody
    public Object updateCrawlSiteById(@RequestBody UpdateCrawlSiteForm updateCrawlSiteReq) {

        return crawlSiteAdapt.updateCrawlSiteById(updateCrawlSiteReq.getSiteId(), updateCrawlSiteReq.getCrawlSiteVO());
    }

    @GetMapping("/delete/{siteId}")
    @ResponseBody
    public Object deleteCrawlSiteById(@PathVariable("siteId") Integer siteId) {

        return crawlSiteAdapt.deleteCrawlSiteById(siteId);
    }
}
