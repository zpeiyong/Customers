package com.dataint.service.datapack.model.param;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DiseaseCountryParam {
    private Long diseaseId;  // 传染病id(暂对应focus_disease表)

    private String diseaseNameCn;  // 传染病名称中文

    private Long countryId;  // 国家id

    private String countryNameCn;  // 国家名称中文

    private Date statisticDate;  // 统计日期("yyyy-mm-dd")

    private String showType;  // 展示周期类型(与focus_disease表中show_type一致)

    private String periodStart;  // 统计时间段开始时间

    private Date periodEnd;  // 统计时间段结束时间

    private Integer confirmTotal;  // 截止periodEnd总确诊人数

    private Integer confirmAdd;  // 时间段内新增确诊

    private Integer deathTotal;  // 截止periodEnd总死亡人数

    private Integer deathAdd;  // 时间段内新增死亡

    private Integer cureTotal;  // 截止periodEnd总治愈人数

    private Integer cureAdd;  // 时间段内新增治愈
}
