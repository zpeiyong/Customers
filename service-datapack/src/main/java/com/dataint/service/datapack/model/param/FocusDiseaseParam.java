package com.dataint.service.datapack.model.param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FocusDiseaseParam {
    private String nameCn;

    private String nameEn;

    private String showType;  // 展示周期类型

    private String firstDateOfPeriod;  // 当前周期第一天日期

    private Integer status;  // 状态(1:可用; 0:不可用)
}
