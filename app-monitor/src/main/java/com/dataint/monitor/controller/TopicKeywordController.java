package com.dataint.monitor.controller;

import com.dataint.monitor.adapt.ITopicKeywordAdapt;
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
    private ITopicKeywordAdapt topicKeywordAdapt;

    @ApiOperation(value = "根据专题id获取关键词", notes = "根据专题id获取关键词")
    @GetMapping("/getKeywordListByTopicId/{topicId}")
    public Object getAllTopic(@PathVariable Integer topicId) {
        log.debug("get all keyword by topicId");
        return topicKeywordAdapt.getKeywordListByTopicId(topicId);
    }

}
