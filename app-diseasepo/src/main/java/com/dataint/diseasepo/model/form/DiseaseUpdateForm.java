package com.dataint.diseasepo.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class DiseaseUpdateForm {
    @ApiModelProperty(value = "传染病id")
    @NotNull
    private Integer diseaseId;

    @ApiModelProperty(value = "传染病icd-10编码")
    private String icd10Code;

    @ApiModelProperty(value = "传染病名称-中文")
    @NotBlank
    private String nameCn;

    @ApiModelProperty(value = "传染病名称-英文")
    @NotBlank
    private String nameEn;

    @ApiModelProperty(value = "传染病状态")
    @NotNull
    private Integer status;
}
