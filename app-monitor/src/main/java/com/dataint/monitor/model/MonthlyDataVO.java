package com.dataint.monitor.model;

import lombok.Data;

@Data
public class MonthlyDataVO {

    private Integer diseaseId;  // 传染病表主键Id

    private String diseaseName;  // 传染病名称

    // 月度(按时间)统计时使用
    // IStatisticService.getMonthlyByTime()
    private String countryNames;  // 国家名称(英文字符‘逗号’分隔)

    // 月度(按国家)统计时使用
    // IStatisticService.getMonthlyByCountry()
    private String months;  // 月份(07-10月)
}
