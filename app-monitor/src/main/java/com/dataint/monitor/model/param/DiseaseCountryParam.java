package com.dataint.monitor.model.param;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DiseaseCountryParam {
    private Long diseaseId;  // 传染病id(暂对应focus_disease表)

    private Long countryId;  // 国家id

    private String countryNameCn;  // 国家名称中文

    private String showType;  // 展示周期类型(与focus_disease表中show_type一致)

    private String periodStart;  // 统计时间段开始时间

    private Long current;

    private Long PageSize;

}
