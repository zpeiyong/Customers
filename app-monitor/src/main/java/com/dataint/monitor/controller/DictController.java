package com.dataint.monitor.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.monitor.service.IDictService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/dict")
@Slf4j
public class DictController {

    @Autowired
    private IDictService dictService;

    /**
     * 舆情等级
     */
    @ApiOperation(value = "获取舆情等级列表", notes = "获取舆情等级信息列表")
    @GetMapping(value = "/outbreakLevels")
    public ResultVO queryOutbreakLevels() {

        return dictService.queryOutbreakLevels();
    }


    /**
     * 大洲/国家 Region/Country
     */
    @ApiOperation(value = "获取大洲列表", notes = "获取大洲信息列表")
    @GetMapping(value = "/regions")
    public ResultVO queryRegions() {

        return dictService.queryRegions();
    }

    @ApiOperation(value = "获取国家列表", notes = "获取国家信息列表")
    @GetMapping(value = "/countries")
    public ResultVO queryCountries() {

        return dictService.queryCountries();
    }


    /**
     * 传染病 Disease
     */
    @ApiOperation(value = "获取传染病列表", notes = "获取传染病列表")
    @GetMapping("/diseases")
    public ResultVO queryDiseases() {

        return dictService.queryDiseases();
    }

    @ApiOperation(value = "根据首字母获取传染病信息", notes = "根据首字母获取传染病信息")
    @ApiImplicitParam(paramType = "query", name = "nameCnFirst", value = "传染病中文首字母", required = true, dataType = "string")
    @GetMapping("/disease/queryByNameCnFirst")
    public ResultVO queryByNameCnFirst(@RequestParam String nameCnFirst) {

        return dictService.queryByNameCnFirst(nameCnFirst);
    }
}
