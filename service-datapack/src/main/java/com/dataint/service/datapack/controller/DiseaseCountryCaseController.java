package com.dataint.service.datapack.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.service.datapack.model.form.DiseaseCountryForm;
import com.dataint.service.datapack.model.param.DiseaseCountryParam;
import com.dataint.service.datapack.model.vo.CountryVO;
import com.dataint.service.datapack.service.IDiseaseCountryCaseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/countryCase")
@Slf4j
public class DiseaseCountryCaseController {
    @Autowired
    private IDiseaseCountryCaseService dcCaseService;

    @RequestMapping(value = "/getCountriesByParam",method = RequestMethod.GET)
    @ApiOperation(value = "过滤国家查询",notes = "列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseId",required = true, value = "传染病id",dataType ="long",paramType = "query"),
            @ApiImplicitParam(name = "showType",required = true, value = "展示周期类型 weekly daily",dataType ="String",paramType = "query"),
            @ApiImplicitParam(name = "periodStart",required = false, value = "统计时间段开始时间",dataType ="String",paramType = "query")
    })
    @ResponseBody
    public ResultVO getCountriesByParam(Long diseaseId, String showType, String periodStart){
        List<CountryVO> countriesByParamList = dcCaseService.getCountriesByParam(diseaseId, showType, periodStart);

        return ResultVO.success(countriesByParamList);
    }

    @RequestMapping(value = "/diseaseCountryCaseList",method = RequestMethod.GET)
    @ApiOperation(value = "病情数据列表查询",notes = "列表查询")
    @ApiImplicitParam(paramType = "query", name = "diseaseCountryParam", value = "病情数据查询参数列表", required = true, dataType = "diseaseCountryParam")
    @ResponseBody
    public ResultVO getDiseaseCountryList(@ModelAttribute DiseaseCountryParam diseaseCountryParam) {

        return ResultVO.success(dcCaseService.listDiseaseCountry(diseaseCountryParam));
    }

    @RequestMapping(value = "/addDiseaseCountry",method = RequestMethod.POST)
    @ApiOperation(value = "增加一个病种数据", notes = "增加一个病种数据")
    @ApiImplicitParam(paramType = "query", name = "DiseaseCountryCase", value = "保存病种数据", required = true, dataType = "DiseaseCountryCase")
    public ResultVO addDiseaseCountry(@RequestBody DiseaseCountryForm country)  {

        return ResultVO.success(dcCaseService.addDiseaseCountryCase(country));
    }

    @ApiOperation(value = "获取地图国家当前病种最新的统计信息", notes = "获取地图国家当前病种最新的统计信息")
    @GetMapping(value = "/getLatestCasesByParam")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseId", required = true, value = "传染病id", dataType ="Long", paramType = "query"),
            @ApiImplicitParam(name = "countryId", required = true, value = "国家ID", dataType ="Long", paramType = "query"),
    })
    public ResultVO getLatestCasesByParam(@RequestParam Long diseaseId, @RequestParam Long countryId) {

        return ResultVO.success(dcCaseService.getLatestCasesByParam(diseaseId, countryId));
    }

    @ApiOperation(value = "确证，死亡周统计", notes = "获取国家当前病种最新的周统计信息")
    @RequestMapping(value = "/getDiseaseForCountryRisk")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseId", required = true, value = "传染病id", dataType ="Integer", paramType = "query"),
            @ApiImplicitParam(name = "countryId", required = true, value = "国家ID", dataType ="Integer", paramType = "query"),
            @ApiImplicitParam(name = "week", required = true, value = "统计周数", dataType ="Integer", paramType = "query"),
    })
    public ResultVO getForCountryRisk(@RequestParam Integer diseaseId, @RequestParam Integer countryId,@RequestParam Integer week) {

        return ResultVO.success(dcCaseService.getForCountryRisk1(diseaseId,countryId,week));
    }
}