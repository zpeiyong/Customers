package com.dataint.monitor.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.monitor.model.DiseaseCountryCase;
import com.dataint.monitor.model.param.DiseaseCountryParam;
import com.dataint.monitor.service.IDiseaseCountryCaseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;



@RestController
@RequestMapping(value = "/countryCase")
@Slf4j
public class DiseaseCountryCaseController {
    @Autowired
    private IDiseaseCountryCaseService countryService;

    @RequestMapping(value = "/getCountriesByParam",method = RequestMethod.GET)
    @ApiOperation(value = "下拉框国家过滤接口",notes = "列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseId",required = true, value = "传染病id",dataType ="Long",paramType = "query"),
            @ApiImplicitParam(name = "showType",required = true, value = "展示周期类型 weekly daily",dataType ="String",paramType = "query"),
            @ApiImplicitParam(name = "periodStart",required = false, value = "统计时间段开始时间",dataType ="String",paramType = "query")
    })
    @ResponseBody
    public Object getCountryByparam(Long diseaseId, String showType,String periodStart) throws ParseException {
       Object countriesByParamList = countryService.getCountriesByParam(diseaseId, showType, periodStart);

        return   countriesByParamList;
    }

    @RequestMapping(value = "/diseaseCountryCaseList",method = RequestMethod.GET)
    @ApiOperation(value = "病情数据列表查询",notes = "列表查询")
    @ApiImplicitParam(paramType = "query", name = "diseaseCountryParam", value = "病情数据查询参数列表", required = true, dataType = "diseaseCountryParam")

    @ResponseBody
    public Object getdieaseCountryList(DiseaseCountryParam  diseaseCountryParam){
        Object listDiseaseCountry = countryService.listDiseaseCountry(diseaseCountryParam);
        return   listDiseaseCountry;
    }

    @RequestMapping(value = "/addDiseaseCountry",method = RequestMethod.POST)
    @ApiOperation(value = "增加一个病种数据", notes = "增加一个病种数据")
    @ApiImplicitParam(paramType = "query", name = "DiseaseCountryCase", value = "保存病种数据", required = true, dataType = "DiseaseCountryCase")
    public Object addDiseaseCountry(@RequestBody DiseaseCountryCase country) {

        Object addDieaseCountry = countryService.addDieaseCountry(country);

        return addDieaseCountry;
    }
}
