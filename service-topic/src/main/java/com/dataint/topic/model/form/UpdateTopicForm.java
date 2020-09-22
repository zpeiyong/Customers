package com.dataint.topic.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel
@Data
public class UpdateTopicForm {

    @ApiModelProperty(value = "专题组名称")
    private String name;

    @ApiModelProperty(value = "专题组拥有的关键词列表")
    private List<String> keywordNames;
}
