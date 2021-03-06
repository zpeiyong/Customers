package com.dataint.service.datapack.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.service.datapack.model.form.DiseaseForm;
import com.dataint.service.datapack.model.form.DiseaseUpdateForm;
import com.dataint.service.datapack.model.param.DiseaseQueryParam;
import com.dataint.service.datapack.service.IDiseaseService;
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

        return ResultVO.success(diseaseService.addDisease(diseaseForm));
    }

    @ApiOperation(value = "更新传染病状态", notes = "更新传染病状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "diseaseId", value = "传染病Id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "传染病状态", required = true, dataType = "long")
    })
    @PutMapping(value = "/status/{diseaseId}")
    public ResultVO updateDiseaseStatus(@PathVariable Long diseaseId, @RequestParam Integer status) {

        return ResultVO.success(diseaseService.updateDiseaseStatus(diseaseId, status));
    }

    @ApiOperation(value = "更新传染病信息", notes = "更新传染病信息")
    @ApiImplicitParam(paramType = "query", name = "diseaseUpdateForm", value = "传染病form表单", required = true, dataType = "DiseaseUpdateForm")
    @PutMapping
    public ResultVO updateDisease(@RequestBody DiseaseUpdateForm diseaseUpdateForm) {

        return ResultVO.success(diseaseService.updateDisease(diseaseUpdateForm));
    }

    @ApiOperation(value = "删除传染病信息", notes = "根据传染病Id删除信息")
    @ApiImplicitParam(paramType = "path", name = "diseaseId", value = "传染病Id", required = true, dataType = "long")
    @DeleteMapping("/{diseaseId}")
    public ResultVO delDisease(@PathVariable Long diseaseId) {

        return ResultVO.success(diseaseService.delDisease(diseaseId));
    }

    @ApiOperation(value = "获取传染病信息", notes = "根据传染病Id获取详细信息")
    @ApiImplicitParam(paramType = "path", name = "diseaseId", value = "传染病Id", required = true, dataType = "long")
    @GetMapping("/{diseaseId}")
    public ResultVO getDisease(@PathVariable Long diseaseId) {

        return ResultVO.success(diseaseService.getDisease(diseaseId));
    }

    @ApiOperation(value = "获取传染病列表", notes = "获取国家列表")
    @ApiImplicitParam(paramType = "query", name = "diseaseQueryParam", value = "传染病查询参数列表", required = true, dataType = "DiseaseQueryParam")
    @GetMapping(value = "/all")
    public ResultVO getDiseases(@ModelAttribute DiseaseQueryParam diseaseQueryParam) {

        return ResultVO.success(diseaseService.getDiseases(diseaseQueryParam));
    }


    /**
     * 新
     */
    @ApiOperation(value = "获取总署关注的传染病列表", notes = "获取总署关注的传染病列表")
    @ApiImplicitParam(paramType = "query", name = "diseaseQueryParam", value = "传染病查询参数列表", required = true, dataType = "DiseaseQueryParam")
    @GetMapping(value = "/queryFocus")
    public ResultVO queryFocus(@ModelAttribute DiseaseQueryParam diseaseQueryParam) {

        return ResultVO.success(diseaseService.queryFocusDiseases(diseaseQueryParam));
    }

    @ApiOperation(value = "获取指定国家传染病病例数相关信息", notes = "获取指定国家传染病病例数相关信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "countryId", value = "国家id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/getCasesByCountryId/{countryId}")
    public ResultVO getCasesByCountryId(@PathVariable Long countryId,
                                        @RequestParam(required = false) String dateStr,
                                        @ModelAttribute PageParam pageParam) {

        return ResultVO.success(diseaseService.getCasesByCountryId(countryId, dateStr, pageParam));
    }

    @ApiOperation(value = "获取指定国家指定传染病病例数相关信息", notes = "获取指定国家指定传染病病例数相关信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "countryId", value = "国家id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "diseaseId", value = "传染病id", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "dateStr", value = "查看日期", dataType = "string")
    })
    @GetMapping("/getCasesByCidAndDid")
    public ResultVO getCasesByCidAndDid(@RequestParam Long countryId,
                                        @RequestParam Long diseaseId,
                                        @RequestParam(required = false) String dateStr) {

        return ResultVO.success(diseaseService.getCasesByCidAndDid(countryId, diseaseId, dateStr));
    }
}
