package com.dataint.monitor.controller;


import com.dataint.monitor.service.IKnowsConfService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/knowsConf")
@Slf4j
public class KnowsConfController {

    @Autowired
    private IKnowsConfService knowsConfService;

    @RequestMapping(value = "/getRelativeDataFx")
    @ApiOperation(value = "根据病情查询其下面附属病情",notes = "根据病情查询其下面附属病情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",required = true, value = "传染病ID",dataType ="long",paramType = "query"),
    })
    public Object getRelativeDataFx(@RequestParam Long id) {
        Object  relativeDataFx= knowsConfService.getRelativeDataFx(id);
        return relativeDataFx;
    }


}
