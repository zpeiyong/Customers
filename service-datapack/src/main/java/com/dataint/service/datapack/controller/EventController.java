package com.dataint.service.datapack.controller;

import com.dataint.cloud.common.model.ResultVO;
import com.dataint.service.datapack.model.form.EventForm;
import com.dataint.service.datapack.model.form.EventUpdateForm;
import com.dataint.service.datapack.model.param.EventQueryParam;
import com.dataint.service.datapack.service.IEventService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/event")
@Slf4j
public class EventController {

    @Autowired
    private IEventService eventService;

    @ApiOperation(value = "新增疫情事件", notes = "新增一个疫情事件")
    @ApiImplicitParam(name = "eventForm", value = "新增疫情事件form表单", required = true, dataType = "EventForm")
    @PostMapping("/add")
    public ResultVO add(@Valid @RequestBody EventForm eventForm) {
        log.debug("Name: {}", eventForm);

        return ResultVO.success(eventService.add(eventForm));
    }

    @ApiOperation(value = "获取疫情事件列表", notes = "获取疫情事件列表")
    @ApiImplicitParam(name = "eventQueryParam", value = "疫情事件查询参数", required = true, dataType = "EventQueryParam")
    @PostMapping("/all")
    public ResultVO getAll(@Valid @RequestBody EventQueryParam eventQueryParam) {

        return eventService.listAll(eventQueryParam);
    }

    @ApiOperation(value = "删除疫情事件", notes = "根据id指定删除疫情事件")
    @ApiImplicitParam(paramType = "path", name = "id", value = "疫情事件ID", required = true, dataType = "long")
    @DeleteMapping(value = "/{id}")
    public ResultVO delete(@PathVariable Long id) {

        return ResultVO.success(eventService.delete(id));
    }

    @ApiOperation(value = "更新停止/开始", notes = "根据id更新停止/开始")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "疫情事件ID", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "enabled", value = "停止/开始", required = true, dataType = "string")
    })
    @PutMapping(value = "/updateEnabled/{id}")
    public ResultVO updateEnabled(@PathVariable Long id, @RequestParam String enabled) {
        log.debug("update enabled with id: {}", id);

        return ResultVO.success(eventService.updateEnabled(id, enabled));
    }

    @ApiOperation(value = "更新疫情事件", notes = "根据疫情id更新疫情事件")
    @ApiImplicitParam(paramType = "query", name = "eventUpdateForm", value = "疫情更新form", required = true, dataType = "EventUpdateForm")
    @PutMapping(value = "/update")
    public ResultVO updateEvent(@RequestBody EventUpdateForm eventUpdateForm) {

        return ResultVO.success(eventService.updateEvent(eventUpdateForm));
    }

}
