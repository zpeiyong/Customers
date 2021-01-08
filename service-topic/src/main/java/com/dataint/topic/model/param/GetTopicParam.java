package com.dataint.topic.model.param;

import com.dataint.cloud.common.model.form.BaseForm;
import com.dataint.cloud.common.model.param.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GetTopicParam extends PageParam implements Serializable  {

    @ApiModelProperty(name = "关键词", value = "keyword")
    private String keyword = "";


}
