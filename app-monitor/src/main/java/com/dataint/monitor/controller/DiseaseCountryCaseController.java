package com.dataint.monitor.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.monitor.model.DiseaseCountryCase;
import com.dataint.monitor.service.IDiseaseCountryCaseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/countryCase")
@Slf4j
public class DiseaseCountryCaseController {
    @Autowired
    private IDiseaseCountryCaseService countryService;

    @RequestMapping(value = "/diseaseCountryCaseList",method = RequestMethod.GET)
    @ApiOperation(value = "病情数据列表查询",notes = "列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseNameCn",required = true, value = "传染病名称中文",dataType ="String",paramType = "query"),
            @ApiImplicitParam(name = "countryNameCn",required = true, value = "国家名称中文",dataType ="String",paramType = "query"),
            @ApiImplicitParam(name = "show_type",required = true, value = "展示周期类型 weekly daily",dataType ="String",paramType = "query"),
            @ApiImplicitParam(name = "periodStart",required = false, value = "统计时间段开始时间",dataType ="Date",paramType = "query")
    })
    @ResponseBody
    public ResultVO<DiseaseCountryCase> getdieaseCountryList(String diseaseNameCn, String countryNameCn, String showType, Date periodStart){
        ResultVO resultVO =ResultVO.success(countryService.listDiseaseCountry(diseaseNameCn, countryNameCn, showType, periodStart));
        return   resultVO;
    }

    @RequestMapping(value = "/addDiseaseCountry",method = RequestMethod.POST)
    @ApiOperation(value = "增加一个病种数据", notes = "增加一个病种数据")
    @ApiImplicitParam(paramType = "query", name = "DiseaseCountryCase", value = "保存病种数据", required = true, dataType = "DiseaseCountryCase")
    public ResultVO addDiseaseCountry(@RequestBody DiseaseCountryCase country) {

        ResultVO resultVO = ResultVO.success(countryService.addDieaseCountry(country));
        return resultVO;
    }
}
