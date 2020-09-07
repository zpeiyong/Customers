package com.dataint.diseasepo.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class CountryUpdateForm {
    @ApiModelProperty(value = "国家id")
    @NotNull
    private Integer countryId;

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
    private Integer regionId;

    @ApiModelProperty(value = "国家纬度")
    private String latitudeStr;

    @ApiModelProperty(value = "国家经度")
    private String longitudeStr;

    @ApiModelProperty(value = "国家状态")
    @NotNull
    private Integer status;
}
