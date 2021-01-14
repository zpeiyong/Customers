package com.dataint.monitor.model;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.monitor.dao.entity.ReportArticle;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class ReportArticleVO extends BaseVO {

    public ReportArticleVO(ReportArticle reportArticle) {
        BeanUtils.copyProperties(reportArticle, this);
    }

    private Long articleId;  // 舆情表id

    private String reportType;  // 报告类型(daily, weekly, monthly, event)

    private Long reportLevelId;  // 报告级别表id
}
