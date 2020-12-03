package com.dataint.monitor.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.monitor.adapt.IDiseaseAdapt;
import com.dataint.monitor.model.form.DiseaseForm;
import com.dataint.monitor.model.form.DiseaseUpdateForm;
import com.dataint.monitor.model.param.DiseaseQueryParam;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/disease")
@Slf4j
public class DiseaseController {

    @Autowired
    private IDiseaseAdapt diseaseAdapt;
    /**
     * 新
     */
    @ApiOperation(value = "获取总署关注的传染病列表", notes = "获取总署关注的传染病列表")
    @ApiImplicitParam(paramType = "query", name = "diseaseQueryParam", value = "传染病查询参数列表", required = true, dataType = "DiseaseQueryParam")
    @GetMapping(value = "/queryFocus")
    public Object queryFocus(@ModelAttribute DiseaseQueryParam diseaseQueryParam) {

        return diseaseAdapt.queryFocusDiseases(diseaseQueryParam);
    }

    @ApiOperation(value = "获取指定国家传染病病例数相关信息", notes = "获取指定国家传染病病例数相关信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "countryId", value = "国家id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/getCasesByCountryId/{countryId}")
    public Object getCasesByCountryId(@PathVariable Long countryId,
                                        @RequestParam(required = false) String dateStr,
                                        @ModelAttribute PageParam pageParam) {

        return diseaseAdapt.getCasesByCountryId(countryId, dateStr, pageParam);
    }

    @ApiOperation(value = "获取指定国家指定传染病病例数相关信息", notes = "获取指定国家指定传染病病例数相关信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "countryId", value = "国家id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/getCasesByCidAndDid")
    public Object getCasesByCidAndDid(@RequestParam Long countryId,
                                        @RequestParam Long diseaseId,
                                        @RequestParam(required = false) String dateStr) {

        return diseaseAdapt.getCasesByCidAndDid(countryId, diseaseId, dateStr);
    }
}
