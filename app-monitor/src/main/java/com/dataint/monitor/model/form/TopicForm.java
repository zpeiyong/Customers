package com.dataint.monitor.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel
@Data
public class TopicForm  {
    @ApiModelProperty(value = "专题组名称")
    private String name;

    @ApiModelProperty(value = "专题组拥有的关键词列表")
    private List<String> keywordNames;

    @ApiModelProperty(value = "用户")
    private Integer userId;

    @ApiModelProperty(value = "用户名称")
    private String username;

}
