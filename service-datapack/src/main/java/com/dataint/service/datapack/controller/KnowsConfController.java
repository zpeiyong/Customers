package com.dataint.service.datapack.controller;


import com.dataint.cloud.common.model.ResultVO;
import com.dataint.service.datapack.service.IKnowsConfService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/knowsConf")
@Slf4j
public class KnowsConfController {
    @Autowired
    private IKnowsConfService knowsConfService;

    @GetMapping(value = "/getRelativeDataFx")
    @ApiOperation(value = "根据病情查询其下面附属病情",notes = "根据病情查询其下面附属病情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",required = true, value = "传染病ID",dataType ="long",paramType = "query"),
    })
    public ResultVO getDiseaseDataTj(@RequestParam Long id) {
        return ResultVO.success(knowsConfService.getRelativeDataFx(id));
    }

}
