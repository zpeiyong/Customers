package com.dataint.service.datapack.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.service.datapack.service.IDictService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/dict")
@Slf4j
public class DictController {

    @Autowired
    private IDictService dictService;

//    @ApiOperation(value = "获取舆情等级列表", notes = "获取舆情等级列表")
//    @GetMapping("/outbreakLevels")
//    public ResultVO queryOutbreakLevels() {
//
//        return ResultVO.success(dictService.queryOutbreakLevels());
//    }

    @ApiOperation(value = "获取大洲列表", notes = "获取大洲信息列表")
    @GetMapping(value = "/queryRegions")
    public ResultVO queryRegions() {

        return ResultVO.success(dictService.queryRegions());
    }

    @ApiOperation(value = "获取国家列表", notes = "获取国家列表")
    @GetMapping("/queryCountries")
    public ResultVO queryCountries() {

        return ResultVO.success(dictService.queryCountries());
    }

    @ApiOperation(value = "获取传染病列表", notes = "获取传染病列表")
    @GetMapping("/queryDiseases")
    public ResultVO queryDiseases() {

        return ResultVO.success(dictService.queryDiseases());
    }

    @ApiOperation(value = "根据首字母获取传染病信息", notes = "根据首字母获取传染病信息")
    @ApiImplicitParam(paramType = "query", name = "nameCnFirst", value = "传染病中文首字母", required = true, dataType = "string")
    @GetMapping("/disease/queryByNameCnFirst")
    public ResultVO queryByNameCnFirst(@RequestParam String nameCnFirst) {

        return ResultVO.success(dictService.queryByNameCnFirst(nameCnFirst));
    }

    @ApiOperation(value = "获取舆情媒体类型列表", notes = "获取舆情媒体类型列表")
    @GetMapping("/queryMediaTypes")
    public ResultVO queryMediaTypes() {

        return ResultVO.success(dictService.queryMediaTypes());
    }

    @ApiOperation(value = "获取舆情类型列表", notes = "获取舆情内容类型列表")
    @GetMapping("/queryArticleTypes")
    public ResultVO queryArticleTypes() {

        return ResultVO.success(dictService.queryArticleTypes());
    }
}
