package com.dataint.monitor.controller;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.utils.JWTUtil;
import com.dataint.monitor.adapt.IApplicationAdapt;
import com.dataint.monitor.model.form.ApplyConditionForm;
import com.dataint.monitor.model.form.ApplyForm;
import com.dataint.monitor.model.form.TopicForm;
import com.dataint.monitor.model.form.UpdateTopicForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/application")
@Api("application")
@Slf4j
public class ApplicationController {

    @Autowired
    private IApplicationAdapt applicationAdapt;

    @ApiOperation(value = "申请新增专题", notes = "申请新增一个专题")
    @ApiImplicitParam(name = "topicForm", value = "新增专题form表单", required = true, dataType = "TopicForm")
    @PostMapping("/applyAddTopic")
    public Object applyAddTopic(@RequestBody TopicForm topicForm, @CookieValue("access_token") String accessToken ) {
        log.debug("apply add a new topic: {}", topicForm);
        Map<String, Object> map = JWTUtil.getUserIdAndName(accessToken);
        if (map.get("userId")!= null)
            topicForm.setUserId((Integer) map.get("userId"));
        if (map.get("username") != null)
            topicForm.setUsername((String) map.get("username"));
        return applicationAdapt.applyAddTopic(topicForm);
    }

    @ApiOperation(value = "申请修改专题", notes = "申请修改一个专题")
    @ApiImplicitParam(name = "topicForm", value = "新增专题form表单", required = true, dataType = "TopicForm")
    @PostMapping("/applyUpdateTopic/{id}")
    public Object applyUpdateTopic(@PathVariable Long id,@RequestBody UpdateTopicForm updateTopicForm) {
        log.debug("apply update a topic : {}", updateTopicForm);
        updateTopicForm.setId(id);
        return applicationAdapt.applyUpdateTopic(updateTopicForm);
    }

    @ApiOperation(value = "获取未处理申请列表", notes = "获取申请列表")
    @GetMapping("/getAllApply")
    public Object getAllApply(ApplyConditionForm applyConditionForm) {
        log.debug("get all unTreated apply {}", applyConditionForm);
        return applicationAdapt.getAllApply(applyConditionForm.getCurrent(), applyConditionForm.getPageSize(),applyConditionForm.getKeyword());
    }

    @ApiOperation(value = "获取处理申请列表", notes = "获取处理申请列表")
    @GetMapping("/getProcessedApply")
    public Object getProcessedApply(ApplyConditionForm applyConditionForm) {
        log.debug("get processed apply {}", applyConditionForm);
        return applicationAdapt.getProcessedApply(applyConditionForm.getCurrent(), applyConditionForm.getPageSize(), applyConditionForm.getKeyword());
    }


    @ApiOperation(value = "通过id获取申请信息", notes = "通过id获取申请信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "topicId", value = "专题id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "id", value = "申请id", required = true, dataType = "int"),
    })
    @GetMapping("/getApplyInfo/{id}")
    public Object getApplyInfo(@PathVariable Long id, @RequestParam(value = "topicId", name = "topicId")Long topicId) {
        log.debug("get apply information by id: {} ", id);
        return applicationAdapt.getApplyInfo(id, topicId);
    }

    @ApiOperation(value = "通过申请", notes = "通过申请")
    @ApiImplicitParam(name = "applyForm", value = "通过申请", required = true, dataType = "applyForm")
    @PostMapping("/saveApply")
    public Object saveApply(@RequestBody ApplyForm applyForm) {
        log.debug("add an apply: {}", applyForm);
        return applicationAdapt.saveApply(applyForm);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "feedback", value = "feedback", required = true, dataType = "string")
    })
    @PostMapping("/refuseApply")
    public Object refuseApply(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInteger("id");
        String feedback = jsonObject.getString("feedback");
        log.debug("refuse an apply:{}", id );
        return  applicationAdapt.refuseApply(id, feedback);
    }

    @ApiOperation(value = "申请删除主题", notes = "申请删除主题")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "int")
    @DeleteMapping("/applyDelTopic/{id}")
    public Object applyDelTopic(@PathVariable Integer id) {
        log.debug("apply del a topic by id: {}", id);
        return applicationAdapt.applyDelTopic(id);
    }
}
