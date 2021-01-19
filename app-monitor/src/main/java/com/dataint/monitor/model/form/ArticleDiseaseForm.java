package com.dataint.monitor.model.form;

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
    private Long diseaseId;

    @ApiModelProperty(value = "国家表主键Id列表")
    private List<Long> countryIdList;

    @ApiModelProperty(value = "疫情开始时间")
    private String diseaseStart;

    @ApiModelProperty(value = "疫情结束时间")
    private String diseaseEnd;

    @ApiModelProperty(value = "当前时间段新增人数")
    private Integer periodConfirm;

    @ApiModelProperty(value = "当前时间段死亡人数")
    private Integer periodDeath;

    @ApiModelProperty(value = "当前时间段治愈人数")
    private Integer periodCure;

    @ApiModelProperty(value = "总确诊人数")
    private Integer confirmCases;

    @ApiModelProperty(value = "总死亡人数")
    private Integer deathCases;

    @ApiModelProperty(value = "总治愈人数")
    private Integer cureCases;

}
