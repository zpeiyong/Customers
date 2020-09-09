package com.dataint.monitor.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.monitor.model.form.DiseaseForm;
import com.dataint.monitor.model.form.DiseaseUpdateForm;
import com.dataint.monitor.model.param.DiseaseQueryParam;
import com.dataint.monitor.service.IDiseaseService;
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
    private IDiseaseService diseaseService;

    @ApiOperation(value = "增加一个传染病信息", notes = "增加一个传染病信息")
    @ApiImplicitParam(paramType = "query", name = "diseaseForm", value = "保存传染病form表单", required = true, dataType = "DiseaseForm")
    @PostMapping
    public ResultVO addDisease(@Valid @RequestBody DiseaseForm diseaseForm) {

        return diseaseService.addDisease(diseaseForm);
    }

    @ApiOperation(value = "更新传染病状态", notes = "更新传染病状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "diseaseId", value = "传染病Id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "传染病状态", required = true, dataType = "long")
    })
    @PutMapping(value = "/status/{diseaseId}")
    public ResultVO updateDiseaseStatus(@PathVariable Integer diseaseId, @RequestParam Integer status) {

        return diseaseService.updateDiseaseStatus(diseaseId, status);
    }

    @ApiOperation(value = "更新传染病信息", notes = "更新传染病信息")
    @ApiImplicitParam(paramType = "query", name = "diseaseUpdateForm", value = "传染病form表单", required = true, dataType = "DiseaseUpdateForm")
    @PutMapping
    public ResultVO updateDisease(@RequestBody DiseaseUpdateForm diseaseUpdateForm) {

        return diseaseService.updateDisease(diseaseUpdateForm);
    }

    @ApiOperation(value = "删除传染病信息", notes = "根据传染病Id删除信息")
    @ApiImplicitParam(paramType = "path", name = "diseaseId", value = "传染病Id", required = true, dataType = "long")
    @DeleteMapping("/{diseaseId}")
    public ResultVO delDisease(@PathVariable Integer diseaseId) {

        return diseaseService.delDisease(diseaseId);
    }

    @ApiOperation(value = "获取传染病详细信息", notes = "根据传染病Id获取详细信息")
    @ApiImplicitParam(paramType = "path", name = "diseaseId", value = "传染病Id", required = true, dataType = "long")
    @GetMapping("/{diseaseId}")
    public ResultVO getDisease(@PathVariable Integer diseaseId) {

        return diseaseService.getDisease(diseaseId);
    }

    @ApiOperation(value = "获取传染病列表", notes = "获取传染病列表")
    @ApiImplicitParam(paramType = "query", name = "diseaseQueryParam", value = "传染病查询参数列表", required = true, dataType = "DiseaseQueryParam")
    @GetMapping(value = "/all")
    public ResultVO getDiseases(@ModelAttribute DiseaseQueryParam diseaseQueryParam) {

        return diseaseService.getDiseases(diseaseQueryParam);
    }
}
