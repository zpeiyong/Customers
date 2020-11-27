package com.dataint.topic.model.form;

import com.dataint.cloud.common.model.param.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ApplyConditionForm extends PageParam implements Serializable {

    @ApiModelProperty(name = "keyword", value = "keyword")
    private String keyword;
}
