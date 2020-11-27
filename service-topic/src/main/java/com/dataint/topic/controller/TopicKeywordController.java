package com.dataint.topic.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.topic.service.ITopicKeywordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topicKeyword")
@Api("topicKeyword")
@Slf4j
public class TopicKeywordController {

    @Autowired
    private ITopicKeywordService topicKeywordService;

    @ApiOperation(value = "根据专题id获取关键词", notes = "根据专题id获取关键词")
    @GetMapping("/getKeywordListByTopicId/{topicId}")
    public ResultVO getAllTopic(@PathVariable Integer topicId) {
        log.debug("get all keyword by topicId");
        return ResultVO.success(topicKeywordService.getKeywordListByTopicId(topicId));
    }

}
