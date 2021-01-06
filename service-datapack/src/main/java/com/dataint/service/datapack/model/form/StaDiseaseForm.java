package com.dataint.service.datapack.model.form;

import com.dataint.cloud.common.model.form.BaseForm;
import lombok.Data;

import java.util.Date;

@Data
public class StaDiseaseForm extends BaseForm {

    public String diseaseName;  // 传染病名称

    public Date timePeriodStart;  // 统计时间段开始

    public Date timePeriodEnd;  // 统计时间段结束

    public String country;  // 统计国家

    public String region;  // 统计区域

    public String firstHumanCase;  // 第一例确诊时间

    public String periodConfirm;  // 当前时间段新增人数

    public String periodDeath;  // 当前时间段死亡人数

    public String periodCure;  // 当前时间段治愈人数

    public String confirmCases;  // 总确诊人数

    public String deathCases;  // 总死亡人数

    public String cureCases;  // 总治愈人数

}
