package com.dataint.monitor.dao.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 报告文章关系表
 */
@Entity
@Table(name = "report_article_relation")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportArticle extends BasePO {

    @Column(name = "article_id", nullable = false)
    private Long articleId;  // 舆情表id

    @Column(name = "report_type")
    private String reportType;  // 报告类型(daily, weekly, monthly, event)

    @Column(name = "report_level_id")
    private Long reportLevelId;  // 报告级别表id

}
