package com.dataint.topic.controller;


import com.dataint.cloud.common.model.ResultVO;
import com.dataint.topic.service.ITopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("/topicArticle")
@Api("topicArticle")
@Slf4j
public class TopicArticleController {

    @Autowired
    private ITopicService topicService;

    @ApiOperation(value = "根据日期查找舆情总体趋势", notes = "根据日期查找舆情总体趋势")
    @ApiImplicitParam(name = "date", value = "日期", required = true, dataType = "date")
    @RequestMapping("/getPopularFeelingsTj")
    public ResultVO getAllTopic(@RequestParam String gmtDate) {

        return ResultVO.success(topicService.getPopularFeelingsTj(gmtDate));
    }

}
