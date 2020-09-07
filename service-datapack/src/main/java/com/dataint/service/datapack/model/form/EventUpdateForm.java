package com.dataint.service.datapack.model.form;

import com.dataint.cloud.common.model.form.BaseForm;
import com.dataint.service.datapack.dao.entity.Event;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel
@Data
public class EventUpdateForm extends BaseForm<Event> {

    @NotNull
    @ApiModelProperty(value = "疫情事件Id")
    private Integer eventId;

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
    private List<SourceUpdateForm> sourceList;
}
