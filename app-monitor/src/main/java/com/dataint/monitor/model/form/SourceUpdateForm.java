package com.dataint.monitor.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class SourceUpdateForm {
    @ApiModelProperty(value = "链接id")
    @NotNull
    private Integer sourceId;

    @NotNull(message = "国家Id不能为空")
    @ApiModelProperty(value = "国家Id")
    private Integer countryId;

    @ApiModelProperty(value = "链接描述")
    private String description;

    @NotEmpty(message = "链接地址不能为空")
    @ApiModelProperty(value = "链接地址")
    private String sourceUrl;
}
