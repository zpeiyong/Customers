package com.dataint.monitor.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.monitor.model.FocusDisease;

import com.dataint.monitor.service.IFocusDiseaseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/focusDisease")
@Slf4j
public class FocusDiseaseController {
    @Autowired
    private IFocusDiseaseService focusDiseaseService;


    @RequestMapping(value = "/focusDiseaseList",method = RequestMethod.GET)
    @ApiOperation(value = "病种周期查询",notes = "病种周期查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",required = true, value = "传染病中文",dataType ="long",paramType = "query"),
            @ApiImplicitParam(name = "showType",required = true, value = "周期类型",dataType ="String",paramType = "query"),
    })
    @ResponseBody
    public ResultVO<FocusDisease> getFoCountryList(Long id, String showType){
        ResultVO  resultVO = ResultVO.success(focusDiseaseService.listFocusDisease(id, showType));
        return  resultVO;
    }

    @RequestMapping(value = "/defaultFocusDisease",method = RequestMethod.GET)
    @ApiOperation(value = "默认病种周期查询",notes = "默认病种周期查询")
    @ResponseBody
    public  ResultVO<FocusDisease> listDefault(){
        ResultVO  resultVO  =ResultVO.success(focusDiseaseService.listFocusDiseaseDefault());
        return   resultVO;
    }

    @RequestMapping(value = "/addFocusDisease",method = RequestMethod.POST)
    @ApiOperation(value = "增加一个病种数据", notes = "增加一个病种数据")
    @ApiImplicitParam(paramType = "query", name = "DiseaseCountryCase", value = "保存病种数据", required = true, dataType = "DiseaseCountryCase")
    public ResultVO addDiseaseCountry(@RequestBody FocusDisease focusDisease){
        ResultVO resultVO = ResultVO.success(focusDiseaseService.addFocusDisease(focusDisease));
        return resultVO;
    }

}
