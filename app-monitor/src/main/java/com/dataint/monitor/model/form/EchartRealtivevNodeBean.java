package com.dataint.monitor.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class EchartRealtivevNodeBean {

    @ApiModelProperty(value = "id")
    @NotNull
    private Integer id;

    @ApiModelProperty(value = "传染病id")
    @NotNull
    private Integer diseaseId;

    @ApiModelProperty(value = "传染病名字")
    @NotBlank
    private String nodeName;

    @ApiModelProperty(value = "传染病相关概述")
    @NotBlank
    private String nodeContent;

    @ApiModelProperty(value = "传染病父节点")
    @NotNull
    private Integer parentId;

}
