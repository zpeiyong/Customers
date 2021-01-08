package com.dataint.topic.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel
@Data
public class UpdateTopicForm {

    @ApiModelProperty(value = "专题组id")
    private Long id;

    @ApiModelProperty(value = "专题组名称")
    private String name;

    @ApiModelProperty(value = "专题组拥有的关键词列表")
    private List<String> keywordNames;

    @ApiModelProperty(value = "是否删除")
    private Boolean ifDeleted;

    @ApiModelProperty(value = "是否启用")
    private Boolean enable;

    @ApiModelProperty(value = "修改说明")
    private String updateDesc;
}
