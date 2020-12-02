package com.dataint.service.datapack.model.form;

import com.dataint.cloud.common.model.form.BaseForm;
import com.dataint.service.datapack.db.entity.Country;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class CountryUpdateForm extends BaseForm<Country> {
    @ApiModelProperty(value = "国家id")
    @NotNull
    private Long countryId;

    @ApiModelProperty(value = "国家编码")
    @NotBlank
    private String code;

    @ApiModelProperty(value = "国家名称-中文")
    @NotBlank
    private String nameCn;

    @ApiModelProperty(value = "国家名称-英文")
    @NotBlank
    private String nameEn;

    @ApiModelProperty(value = "大洲Id")
    @NotNull
    private Long regionId;

    @ApiModelProperty(value = "国家纬度")
    private String latitude;

    @ApiModelProperty(value = "国家经度")
    private String longitude;

    @ApiModelProperty(value = "国家状态")
    @NotNull
    private Integer status;
}
