package com.dataint.topic.controller;

import com.dataint.cloud.common.model.Constants;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.topic.db.entity.Topic;
import com.dataint.topic.model.form.GetTopicForm;
import com.dataint.topic.model.form.TopicForm;
import com.dataint.topic.model.form.UpdateTopicForm;
import com.dataint.topic.service.ITopicKeywordService;
import com.dataint.topic.service.ITopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/topic")
@Api("topic")
@Slf4j
public class TopicController {
    @Autowired
    private ITopicService topicService;
    @Autowired
    private ITopicKeywordService topicKeywordService;

    @ApiOperation(value = "新增专题", notes = "新增一个专题")
    @ApiImplicitParam(name = "topicForm", value = "新增专题form表单", required = true, dataType = "TopicForm")
    @PostMapping("/addTopic")
    public ResultVO addTopic(@RequestBody TopicForm topicForm) {
        log.debug("add topicForm: {}", topicForm);
        Object topic = topicService.addTopic(topicForm);
        if (topic instanceof String) {
            return  ResultVO.fail(topic);
        }
        return ResultVO.success(topic);
    }


    @ApiOperation(value = "删除专题", notes = "删除专题")
    @ApiImplicitParam(name = "id", value = "专题组id", required = true, dataType = "long")
    @DeleteMapping("/delTopic/{id}")
    public ResultVO delTopic(@PathVariable Long id){
        log.debug("delete a  topic by id",id);

        return ResultVO.success(topicService.delTopic(id));
    }

    @ApiOperation(value = "恢复专题", notes = "恢复专题")
    @ApiImplicitParam(name = "id", value = "专题组id", required = true, dataType = "long")
    @PutMapping("/recoveryTopic/{id}")
    public ResultVO recoveryTopic(@PathVariable Long id) {
        log.debug("recovery a topic by id:{}", id);
        return ResultVO.success(topicService.recoveryTopic(id));
    }

    @ApiOperation(value = "修改专题组" ,notes ="修改专题组")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "专题组ID", required = true, dataType = "long"),
            @ApiImplicitParam(name = "updateTopicForm", value = "修改专题form表单", required = true, dataType = "UpdateTopicForm")})
    @PutMapping(value = "/updateTopic/{id}")
    public ResultVO updateTopic(@PathVariable Long id,@RequestBody UpdateTopicForm updateTopicForm){
        log.debug("update Topic by id",id);
        updateTopicForm.setId(id);
        Object topic = topicService.updateTopic(updateTopicForm);
        if (topic instanceof  String)
            return ResultVO.fail(topic);
        return ResultVO.success(topic);
    }

    @ApiOperation(value = "获取激活专题组", notes = "获取激活专题组")
    @GetMapping("/getAllActiveTopic")
    public ResultVO getAllActiveTopic() {
        log.debug("get all topic");
        return ResultVO.success(topicService.getAllActiveTopic());
    }

    @ApiOperation(value = "获取历史专题组" , notes = "获取历史专题组")
    @GetMapping("/getAllDelTopic")
    public ResultVO getAllDeleteTopic(){
        log.debug("get all deltopic");

        return ResultVO.success(topicService.getAllDeleteTopic());
    }

    @ApiOperation(value = "获取所有专题组", notes = "获取所有专题组")
    @GetMapping("/getAllTopic")
    public ResultVO getAllTopic (GetTopicForm topicForm) {
        log.debug("get all topic List by:{}", topicForm);
        return ResultVO.success(topicService.getAllTopic(topicForm.getCurrent(), topicForm.getPageSize(), topicForm.getKeyword()));
    }
}
