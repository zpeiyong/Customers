package com.dataint.diseasepo.dao.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "statistic_basic")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticBasic extends BasePO {

    @Column(name = "disease_total_cnt")
    private Integer diseaseTotalCnt;  // 疫情总数

    @Column(name = "type_yearly_cnt")
    private Integer typeYearlyCnt;  // 今年爆发疫情类别种数

    @Column(name = "country_yearly_cnt")
    private Integer countryYearlyCnt;  // 今年疫情爆发国家(地区)个数

    @Column(name = "case_yearly_cnt")
    private Integer caseYearlyCnt;  // 今年病例个数

    @Column(name = "death_yearly_cnt")
    private Integer deathYearlyCnt;  // 今年死亡人数

    @Column(name = "disease_daily_cnt")
    private Integer diseaseDailyCnt;  // 今日疫情数

    @Column(name = "typeDailyCnt")
    private Integer typeDailyCnt;  // 今日疫情类别种数

    @Column(name = "countryDailyCnt")
    private Integer countryDailyCnt;  // 今日疫情爆发国家(地区)个数

    @Column(name = "statisticStart")
    private Date statisticStart;  // 统计起始时间

    @Column(name = "statisticEnd")
    private Date statisticEnd;  // 统计结束时间
}
