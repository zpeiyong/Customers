package com.dataint.service.datapack.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.service.datapack.service.IWarningService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/warning")
@Slf4j
public class WarningController {

    @Autowired
    private IWarningService warningService;

    @ApiOperation(value = "获取[首页][BI大屏]预警信息列表", notes = "获取[首页][BI大屏]预警信息列表")
    @GetMapping("/getWarningInfos")
    public ResultVO getWarningInfos() {

        return ResultVO.success(warningService.getWarningInfos());
    }
}
