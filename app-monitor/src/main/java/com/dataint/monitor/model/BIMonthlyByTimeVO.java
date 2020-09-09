package com.dataint.monitor.model;

import lombok.Data;

import java.util.List;

@Data
public class BIMonthlyByTimeVO {

    private String month;  // 月份(01, 02, 03, ...)

    private List<MonthlyDataVO> dataList;  // 月度统计数据列表
}
