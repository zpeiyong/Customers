package com.dataint.service.datapack.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.service.datapack.db.entity.FocusDisease;
import com.dataint.service.datapack.model.param.FocusDiseaseParam;
import com.dataint.service.datapack.service.IFocusDiseaseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/focusDisease")
@Slf4j
public class FocusDiseaseController {
    @Autowired
    private IFocusDiseaseService   focusDiseaseService;


    @RequestMapping(value = "/focusDiseaseList",method = RequestMethod.GET)
    @ApiOperation(value = "病种周期查询",notes = "病种周期查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseNameCn",required = true, value = "传染病中文",dataType ="String",paramType = "query"),
            @ApiImplicitParam(name = "showType",required = true, value = "周期类型",dataType ="String",paramType = "query"),
    })
    @ResponseBody
    public List<FocusDisease> getFoCountryList(String nameCn, String showType){
        FocusDiseaseParam focusDiseaseParam = new FocusDiseaseParam();
        focusDiseaseParam.setNameCn(nameCn);
        focusDiseaseParam.setShowType(showType);
        List<FocusDisease> focusDiseases = focusDiseaseService.listFocusDisease(focusDiseaseParam);
        return  focusDiseases;
    }

    @RequestMapping(value = "/defaultFocusDisease",method = RequestMethod.GET)
    @ApiOperation(value = "默认病种周期查询",notes = "默认病种周期查询")
    @ResponseBody
    public  List<FocusDisease> listDefault(){
        List<FocusDisease> diseaseDefault = focusDiseaseService.listFocusDiseaseDefault();
        return   diseaseDefault;
    }

    @RequestMapping(value = "/addFocusDisease",method = RequestMethod.POST)
    @ApiOperation(value = "增加一个病种数据", notes = "增加一个病种数据")
    @ApiImplicitParam(paramType = "query", name = "DiseaseCountryCase", value = "保存病种数据", required = true, dataType = "DiseaseCountryCase")
    public ResultVO addDiseaseCountry(@RequestBody FocusDisease focusDisease){
        ResultVO resultVO = ResultVO.success(focusDiseaseService.addFocusDisease(focusDisease));
        return resultVO;
    }
}
