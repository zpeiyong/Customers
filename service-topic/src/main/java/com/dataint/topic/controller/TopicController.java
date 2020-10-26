package com.dataint.topic.controller;

import com.dataint.cloud.common.model.Constants;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.topic.db.entity.Topic;
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
    public ResultVO<Topic> add(@Valid @RequestBody TopicForm topicForm,@RequestHeader(value = Constants.AUTHORIZE_ACCESS_TOKEN) String accessToken) {
        log.debug("add topicForm: {}", topicForm);

        Topic topic = new Topic();
        BeanUtils.copyProperties(topicForm,topic);
        return ResultVO.success(topicService.addTopic(topic));
    }


    @ApiOperation(value = "逻辑删除专题", notes = "逻辑删除专题")
    @ApiImplicitParam(name = "id", value = "专题组id", required = true, dataType = "long")
    @DeleteMapping("/deleteLogic/{id}")
    public ResultVO deleteLogic(@PathVariable Long id){
        log.debug("deleteLogic topic by id",id);

        return ResultVO.success(topicService.deleteLogic(id));
    }


    @ApiOperation(value = "真实删除专题组", notes = "真实删除专题组")
    @ApiImplicitParam(name = "id", value = "专题组id", required = true, dataType = "long")
    @DeleteMapping("/deleteReal/{id}")
    public ResultVO deleteReal(@PathVariable Long id){
        log.debug("deleteReal topic by id");

        return ResultVO.success(topicService.deleteReal(id));
    }





    @ApiOperation(value = "修改专题组" ,notes ="修改专题组")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "专题组ID", required = true, dataType = "long"),
            @ApiImplicitParam(name = "updateTopicForm", value = "修改专题form表单", required = true, dataType = "UpdateTopicForm")})
    @PutMapping(value = "/updateTopic/{id}")
    public ResultVO updateTopic(@PathVariable Long id,@Valid @RequestBody UpdateTopicForm updateTopicForm){
        log.debug("updateTopic by id",id);

        Topic topic = new Topic();
        BeanUtils.copyProperties(updateTopicForm,topic);
        topic.setId(id);
        topicService.update(topic);
        return ResultVO.success();
    }


    @ApiOperation(value = "获取专题组" , notes = "获取专题组")
    @GetMapping("/allTopic")
    public ResultVO getTopic(){
        log.debug("get all topic");

        return ResultVO.success(topicKeywordService.topicKeyWordList());
    }


    @ApiOperation(value = "获取历史专题组" , notes = "获取历史专题组")
    @GetMapping("/allDelTopic")
    public ResultVO getdelTopic(){
        log.debug("get all deltopic");

        return ResultVO.success(topicKeywordService.delTopicList());
    }
}
