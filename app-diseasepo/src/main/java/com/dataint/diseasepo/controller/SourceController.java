package com.dataint.diseasepo.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.diseasepo.model.form.SourceForm;
import com.dataint.diseasepo.model.form.SourceUpdateForm;
import com.dataint.diseasepo.service.ISourceService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/source")
@Slf4j
public class SourceController {
    @Autowired
    private ISourceService sourceService;

    @ApiOperation(value = "新增数据来源链接", notes = "新增一个数据来源链接")
    @ApiImplicitParam(name = "sourceForm", value = "新增数据来源链接form表单", required = true, dataType = "SourceForm")
    @PostMapping("/add")
    public ResultVO add(@Valid @RequestBody SourceForm sourceForm) {
        log.debug("Name: {}", sourceForm);

        return sourceService.add(sourceForm);
    }

    @ApiOperation(value = "更新数据来源链接", notes = "更新数据来源链接")
    @ApiImplicitParam(name = "sourceUpdateForm", value = "数据来源链接updateForm表单", required = true, dataType = "SourceUpdateForm")
    @PutMapping("/update")
    public ResultVO updateSource(@Valid @RequestBody SourceUpdateForm sourceUpdateForm) {
        log.debug("Name: {}", sourceUpdateForm);

        return sourceService.updateSource(sourceUpdateForm);
    }

    @ApiOperation(value = "删除数据来源链接", notes = "更新数据来源链接")
    @ApiImplicitParam(name = "sourceId", value = "数据来源链接Id", required = true, dataType = "long")
    @DeleteMapping("/delete/{sourceId}")
    public ResultVO delSource(@PathVariable Integer sourceId) {
        log.debug("Name: {}", sourceId);

        return sourceService.delSource(sourceId);
    }

}
