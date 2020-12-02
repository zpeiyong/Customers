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
 * 基本统计总表(每日)
 */
@Entity
@Table(name = "statistic_basic")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticBasic extends BasePO {

    @Column(name = "disease_id")
    private Long diseaseId;  // 传染病id(暂对应focus_disease表)

    @Column(name = "statistic_date")
    private Date statisticDate;  // 统计日期

    @Column(name = "country_total")
    private Integer countryTotal = 0;  // 传播国家数量-总数

    @Column(name = "country_add")
    private Integer countryAdd = 0;  // 传播国家数量-增加

    @Column(name = "event_total")
    private Integer eventTotal = 0;  // 事件数量-总数

    @Column(name = "event_add")
    private Integer eventAdd = 0;  // 事件数量-增加

    @Column(name = "article_total")
    private Integer articleTotal = 0;  // 舆情数量-总数

    @Column(name = "article_add")
    private Integer articleAdd = 0;  // 舆情数量-新增

    @Column(name = "confirm_total")
    private Integer confirmTotal = 0;  // 总确诊人数

    @Column(name = "confirm_add")
    private Integer confirmAdd = 0;  // 新增确诊

    @Column(name = "death_total")
    private Integer deathTotal = 0;  // 总死亡人数

    @Column(name = "death_add")
    private Integer deathAdd = 0;  // 新增死亡

    @Column(name = "cure_total")
    private Integer cureTotal = 0;  // 总治愈人数

    @Column(name = "cure_add")
    private Integer cureAdd = 0;  // 新增治愈
}
