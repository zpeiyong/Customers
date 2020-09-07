package com.dataint.diseasepo.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel
@Data
public class EventForm {
    @NotBlank(message = "疫情事件名称不能为空")
    @ApiModelProperty(value = "疫情事件名称")
    private String eventName;

    @NotNull
    @ApiModelProperty(value = "疫情类型表主键id")
    private Integer diseaseId;

    @NotBlank
    @ApiModelProperty(value = "疫情事件开始时间")
    private String eventStart;

    @ApiModelProperty(value = "疫情事件结束时间")
    private String eventEnd;

    @ApiModelProperty(value = "疫情事件数据来源列表")
    private List<SourceForm> sourceList;
}
