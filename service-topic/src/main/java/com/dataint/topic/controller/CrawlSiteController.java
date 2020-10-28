package com.dataint.topic.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.topic.model.vo.CrawlSiteVO;
import com.dataint.topic.model.form.UpdateCrawlSiteForm;
import com.dataint.topic.service.ICrawlSiteService;

import io.swagger.annotations.ApiOperation;
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

    /**
     * 查询所有信息来源
     *
     * @auther: Tim_Huo
     * @return: ResultVO
     * @date: 2020/10/28 10:29 上午
     */
    @ApiOperation(value = "获取信息来源" , notes = "获取信息来源")
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
