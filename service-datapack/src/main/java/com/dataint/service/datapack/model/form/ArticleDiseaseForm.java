package com.dataint.service.datapack.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel
@Data
public class ArticleDiseaseForm {
    @ApiModelProperty(value = "传染病表主键Id")
    @NotNull
    private Integer diseaseId;

    @ApiModelProperty(value = "国家表主键Id列表")
    private List<Integer> countryIdList;

    @ApiModelProperty(value = "疫情开始时间")
    private String diseaseStart;

    @ApiModelProperty(value = "疫情结束时间")
    private String diseaseEnd;

    @ApiModelProperty(value = "新增病例")
    private Integer newCases;

    @ApiModelProperty(value = "累计病例")
    private Integer cumulativeCases;

    @ApiModelProperty(value = "确诊病例")
    private Integer confirmedCases;

    @ApiModelProperty(value = "疑似病例")
    private Integer suspectedCases;

    @ApiModelProperty(value = "死亡病例")
    private Integer deathToll;
}
