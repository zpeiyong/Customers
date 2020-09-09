package com.dataint.monitor.model;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.monitor.dao.entity.Report;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class ReportVO extends BaseVO {

    public ReportVO(Report report) {
        BeanUtils.copyProperties(report, this);
    }

    private String title;  // 简报名称

    private String reportType;  // 简报类型(daily, weekly, monthly, yearly)
}
