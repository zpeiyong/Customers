package com.dataint.monitor.controller;

import com.dataint.monitor.adapt.IWarningAdapt;
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
    private IWarningAdapt warningAdapt;

    @ApiOperation(value = "获取[首页][BI大屏]预警信息列表", notes = "获取[首页][BI大屏]预警信息列表")
    @GetMapping("/getWarningInfos")
    public Object getWarningInfos() {

        return warningAdapt.getWarningInfos();
    }
}
