package com.dataint.service.datapack.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.service.datapack.db.entity.DiseaseCountryCase;
import com.dataint.service.datapack.model.param.DiseaseCountryParam;
import com.dataint.service.datapack.model.vo.CountryVO;
import com.dataint.service.datapack.service.IDiseaseCountryCaseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping(value = "/countryCase")
@Slf4j
public class DiseaseCountryCaseController {
    @Autowired
    private IDiseaseCountryCaseService countryCaseService;

    @RequestMapping(value = "/getCountriesByParam",method = RequestMethod.GET)
    @ApiOperation(value = "过滤国家查询",notes = "列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseId",required = true, value = "传染病id",dataType ="long",paramType = "query"),
            @ApiImplicitParam(name = "showType",required = true, value = "展示周期类型 weekly daily",dataType ="String",paramType = "query"),
            @ApiImplicitParam(name = "periodStart",required = false, value = "统计时间段开始时间",dataType ="String",paramType = "query")
    })
    @ResponseBody
    public ResultVO<CountryVO> getCountryByparam(Long diseaseId, String showType, String periodStart) throws  ParseException{
        List<CountryVO> countriesByParamList = countryCaseService.getCountriesByParam(diseaseId, showType, periodStart);

        ResultVO resultVO =ResultVO.success(countriesByParamList);

        return   resultVO;
    }






    @RequestMapping(value = "/diseaseCountryCaseList",method = RequestMethod.GET)
    @ApiOperation(value = "病情数据列表查询",notes = "列表查询")

    @ApiImplicitParam(paramType = "query", name = "diseaseCountryParam", value = "病情数据查询参数列表", required = true, dataType = "diseaseCountryParam")

    @ResponseBody
        public ResultVO<DiseaseCountryCase> getdieaseCountryList(@ModelAttribute DiseaseCountryParam diseaseCountryParam) {

        ResultVO resultVO =ResultVO.success(countryCaseService.listDiseaseCountry(diseaseCountryParam));
        return   resultVO;
    }

    @RequestMapping(value = "/addDiseaseCountry",method = RequestMethod.POST)
    @ApiOperation(value = "增加一个病种数据", notes = "增加一个病种数据")
    @ApiImplicitParam(paramType = "query", name = "DiseaseCountryCase", value = "保存病种数据", required = true, dataType = "DiseaseCountryCase")
    public ResultVO addDiseaseCountry(@RequestBody DiseaseCountryCase country)  {

        ResultVO resultVO = ResultVO.success(countryCaseService.addDieaseCountry(country));
        return resultVO;
    }
}
