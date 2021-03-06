package com.dataint.service.datapack.model.form;

import com.dataint.cloud.common.model.form.BaseForm;
import com.dataint.service.datapack.db.entity.Source;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class SourceForm extends BaseForm<Source> {

    @NotNull(message = "国家Id不能为空")
    @ApiModelProperty(value = "国家Id")
    private Long countryId;

    @ApiModelProperty(value = "链接描述")
    private String description;

    @NotEmpty(message = "链接地址不能为空")
    @ApiModelProperty(value = "链接地址")
    private String sourceUrl;
}
