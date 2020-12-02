package com.dataint.service.datapack.db.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 全球传染病风险指数统计表(每日)
 * (与病种无关)
 */
@Entity
@Table(name = "statistic_global_risk")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticGlobalRisk extends BasePO {

    private static final long serialVersionUID = 5416614290535278521L;

    @Column(name = "statistic_date")
    private Date statisticDate;  // 统计日期(t-1天的日期)

    @Column(name = "global_risk_score")
    private Double globalRiskScore;  // 全球风险指数
}