package com.dataint.diseasepo.model;

import lombok.Data;

import java.util.List;

@Data
public class BIMonthlyByCountryVO {

    private String countryName;  // 国家名称

    private List<MonthlyDataVO> dataList;  // 月度统计数据列表
}
