package com.dataint.monitor.controller;

import com.dataint.monitor.adapt.ITopicAdapt;
import com.dataint.monitor.model.form.GetTopicForm;
import com.dataint.monitor.model.form.TopicForm;
import com.dataint.monitor.model.form.UpdateTopicForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic")
@Api("topic")
@Slf4j
public class TopicController {

    @Autowired
    private ITopicAdapt topicAdapt;


    @ApiOperation(value = "新增专题", notes = "新增一个专题")
    @ApiImplicitParam(name = "topicForm", value = "新增专题form表单", required = true, dataType = "TopicForm")
    @PostMapping("/addTopic")
    public Object addTopic(@RequestBody TopicForm topicForm) {
        log.debug("add topicForm: {}", topicForm);
        // 解析token获取userId
//        Integer userId = JWTUtil.getUserId(accessToken);
        return topicAdapt.addTopic(topicForm);
    }


    @ApiOperation(value = "删除专题", notes = "删除专题")
    @ApiImplicitParam(name = "id", value = "专题组id", required = true, dataType = "long")
    @DeleteMapping("/delTopic/{id}")
    public Object delTopic(@PathVariable Long id){
        log.debug("delete a  topic by id:{}",id);

        return topicAdapt.delTopic(id);
    }

    @ApiOperation(value = "恢复专题", notes = "恢复专题")
    @ApiImplicitParam(name = "id", value = "专题组id", required = true, dataType = "long")
    @DeleteMapping("/recoveryTopic/{id}")
    public Object recoveryTopic(@PathVariable Long id) {
        log.debug("recovery a topic by id:{}", id);
        return topicAdapt.recoveryTopic(id);
    }

    @ApiOperation(value = "修改专题组" ,notes ="修改专题组")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "专题组ID", required = true, dataType = "long"),
            @ApiImplicitParam(name = "updateTopicForm", value = "修改专题form表单", required = true, dataType = "UpdateTopicForm")})
    @PutMapping(value = "/updateTopic/{id}")
    public Object updateTopic(@PathVariable Long id,@RequestBody UpdateTopicForm updateTopicForm){
        log.debug("update Topic by id:{}",id);
        updateTopicForm.setId(id);
        return topicAdapt.updateTopic(updateTopicForm);
    }

    @ApiOperation(value = "获取激活专题组", notes = "获取激活专题组")
    @GetMapping("/getAllActiveTopic")
    public Object getAllActiveTopic() {
        log.debug("get all topic");
        return topicAdapt.getAllActiveTopic();
    }

    @ApiOperation(value = "获取历史专题组" , notes = "获取历史专题组")
    @GetMapping("/getAllDelTopic")
    public Object getAllDeleteTopic(){
        log.debug("get all delTopic");

        return topicAdapt.getAllDeleteTopic();
    }

    @ApiOperation(value = "获取所有专题组", notes = "获取所有专题组")
    @GetMapping("/getAllTopic")
    public Object getAllTopic (GetTopicForm topicForm) {
        log.debug("get all topic List by:{}", topicForm);
        return topicAdapt.getAllTopic(topicForm.getCurrent(), topicForm.getPageSize(), topicForm.getKeyword());
    }
}
