package com.dataint.diseasepo.model.param;

import com.dataint.cloud.common.model.param.PageParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportQueryParam extends PageParam {

    private String reportDate;  // 简报日期

    private String keyword;  // 关键词

    @NotNull
    private String reportType;  // 简报类型(daily,weekly,monthly,yearly)
}
