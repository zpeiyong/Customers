package com.dataint.service.datapack.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.service.datapack.model.form.CountryForm;
import com.dataint.service.datapack.model.form.CountryUpdateForm;
import com.dataint.service.datapack.model.param.CountryQueryParam;
import com.dataint.service.datapack.service.ICountryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/country")
@Slf4j
public class CountryController {

    @Autowired
    private ICountryService countryService;

    @ApiOperation(value = "增加一个国家信息", notes = "增加一个国家信息")
    @ApiImplicitParam(paramType = "query", name = "countryForm", value = "保存国家form表单", required = true, dataType = "CountryForm")
    @PostMapping
    public ResultVO addCountry(@Valid @RequestBody CountryForm countryForm) {

        return ResultVO.success(countryService.addCountry(countryForm));
    }

    @ApiOperation(value = "更新国家状态", notes = "更新国家状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "countryId", value = "国家Id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "国家状态", required = true, dataType = "long")
    })
    @PutMapping(value = "/status/{countryId}")
    public ResultVO updateCountryStatus(@PathVariable Long countryId, @RequestParam Integer status) {

        return ResultVO.success(countryService.updateCountryStatus(countryId, status));
    }

    @ApiOperation(value = "更新国家信息", notes = "更新一个国家信息")
    @ApiImplicitParam(paramType = "query", name = "countryUpdateForm", value = "国家form表单", required = true, dataType = "CountryUpdateForm")
    @PutMapping
    public ResultVO updateCountry(@RequestBody CountryUpdateForm countryUpdateForm) {

        return ResultVO.success(countryService.updateCountry(countryUpdateForm));
    }

    @ApiOperation(value = "删除国家信息", notes = "根据国家Id删除信息")
    @ApiImplicitParam(paramType = "path", name = "countryId", value = "国家id", required = true, dataType = "long")
    @DeleteMapping("/{countryId}")
    public ResultVO delCountry(@PathVariable Long countryId) {

        return ResultVO.success(countryService.delCountry(countryId));
    }

    @ApiOperation(value = "获取国家详细信息", notes = "根据国家Id获取详细信息")
    @ApiImplicitParam(paramType = "path", name = "countryId", value = "国家id", required = true, dataType = "long")
    @GetMapping("/{countryId}")
    public ResultVO getCountry(@PathVariable Long countryId) {

        return ResultVO.success(countryService.getCountry(countryId));
    }

    @ApiOperation(value = "获取国家列表", notes = "获取国家列表")
    @ApiImplicitParam(paramType = "query", name = "countryQueryParam", value = "国家查询参数列表", required = true, dataType = "CountryQueryParam")
    @GetMapping(value = "/all")
    public ResultVO getCountries( CountryQueryParam countryQueryParam) {

        return ResultVO.success(countryService.getCountries(countryQueryParam));
    }
}
