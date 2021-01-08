package com.dataint.topic.controller;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.topic.model.form.ApplyConditionForm;
import com.dataint.topic.model.form.ApplyForm;
import com.dataint.topic.model.form.TopicForm;
import com.dataint.topic.model.form.UpdateTopicForm;
import com.dataint.topic.service.IApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/application")
@Api("application")
@Slf4j
public class ApplicationController {

    @Autowired
    private IApplicationService applicationService;

    @ApiOperation(value = "申请新增专题", notes = "申请新增一个专题")
    @ApiImplicitParam(name = "topicForm", value = "新增专题form表单", required = true, dataType = "TopicForm")
    @PostMapping("/applyAddTopic")
    public ResultVO applyAddTopic(@RequestBody TopicForm topicForm) {
        log.debug("apply add a new topic: {}", topicForm);

        return ResultVO.success(applicationService.applyAddTopic(topicForm));
    }

    @ApiOperation(value = "申请修改专题", notes = "申请修改一个专题")
    @ApiImplicitParam(name = "topicForm", value = "新增专题form表单", required = true, dataType = "TopicForm")
    @PostMapping("/applyUpdateTopic/{id}")
    public ResultVO applyUpdateTopic(@PathVariable Long id, @RequestBody UpdateTopicForm updateTopicForm) {
        log.debug("apply update a topic : {}", updateTopicForm);
        updateTopicForm.setId(id);

        return ResultVO.success(applicationService.applyUpdateTopic(updateTopicForm));
    }

    @ApiOperation(value = "获取未处理申请列表", notes = "获取申请列表")
    @GetMapping("/getAllApply")
    public ResultVO getAllApply(ApplyConditionForm applyConditionForm) {
        log.debug("get all unTreated apply {}", applyConditionForm);

        return applicationService.getAllApply(applyConditionForm.getCurrent(), applyConditionForm.getPageSize(),applyConditionForm.getKeyword());
    }

    @ApiOperation(value = "获取处理申请列表", notes = "获取处理申请列表")
    @GetMapping("/getProcessedApply")
    public ResultVO getProcessedApply(ApplyConditionForm applyConditionForm) {
        log.debug("get processed apply {}", applyConditionForm);

        return ResultVO.success(applicationService.getProcessedApply(applyConditionForm.getCurrent(), applyConditionForm.getPageSize(), applyConditionForm.getKeyword()));
    }

    @ApiOperation(value = "通过id获取申请信息", notes = "通过id获取申请信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "topicId", value = "专题id", required = true, dataType = "long"),
            @ApiImplicitParam(name = "id", value = "申请id", required = true, dataType = "long"),
    })
    @GetMapping("/getApplyInfo/{id}")
    public ResultVO getApplyInfo(@PathVariable Long id, @RequestParam(value = "topicId", name = "topicId")Long topicId) {
        log.debug("get apply information by id: {} ", id);

        return ResultVO.success(applicationService.getApplyInfo(id, topicId));
    }

    @ApiOperation(value = "通过申请", notes = "通过申请")
    @ApiImplicitParam(name = "applyForm", value = "通过申请", required = true, dataType = "applyForm")
    @PostMapping("/saveApply")
    public ResultVO saveApply(@RequestBody ApplyForm applyForm) {
        log.debug("add an apply: {}", applyForm);

        return ResultVO.success(applicationService.saveApply(applyForm));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "long"),
            @ApiImplicitParam(name = "feedback", value = "feedback", required = true, dataType = "string")
    })
    @PostMapping("/refuseApply")
    public ResultVO refuseApply(@RequestBody JSONObject jsonObject) {
        Long id = jsonObject.getLong("id");
        String feedback = jsonObject.getString("feedback");
        log.debug("refuse an apply:{}", id );

        return  ResultVO.success(applicationService.refuseApply(id, feedback));
    }

    @ApiOperation(value = "申请删除主题", notes = "申请删除主题")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "long")
    @DeleteMapping("/applyDelTopic/{id}")
    public ResultVO applyDelTopic(@PathVariable Long id) {
        log.debug("apply del a topic by id: {}", id);

        return ResultVO.success(applicationService.applyDelTopic(id));
    }
}
