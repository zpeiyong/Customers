package com.dataint.monitor.controller;

import com.dataint.monitor.adapt.IDictAdapt;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/dict")
@Slf4j
public class DictController {

    @Autowired
    private IDictAdapt dictAdapt;

//    @ApiOperation(value = "获取舆情等级列表", notes = "获取舆情等级列表")
//    @GetMapping("/outbreakLevels")
//    public ResultVO queryOutbreakLevels() {
//
//        return ResultVO.success(dictService.queryOutbreakLevels());
//    }

    @ApiOperation(value = "获取大洲列表", notes = "获取大洲信息列表")
    @GetMapping(value = "/queryRegions")
    public Object queryRegions() {

        return dictAdapt.queryRegions();
    }


    @ApiOperation(value = "获取舆情媒体类型列表", notes = "获取舆情媒体类型列表")
    @GetMapping("/queryMediaTypes")
    public Object queryMediaTypes() {

        return dictAdapt.queryMediaTypes();
    }

    @ApiOperation(value = "获取舆情类型列表", notes = "获取舆情内容类型列表")
    @GetMapping("/queryArticleTypes")
    public Object queryArticleTypes() {

        return dictAdapt.queryArticleTypes();
    }
}
