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
 * 病种国家病例数统计表(不同传染病统计周期不同-showType)
 */
@Entity
@Table(name = "disease_country_case")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseCountryCase extends BasePO {

    @Column(name = "id")
    private   Long  id;
    @Column(name = "disease_id")
    private Long diseaseId;  // 传染病id(暂对应focus_disease表)

    @Column(name = "disease_name_cn")
    private String diseaseNameCn;  // 传染病名称中文

    @Column(name = "country_id")
    private Long countryId;  // 国家id

    @Column(name = "country_name_cn")
    private String countryNameCn;  // 国家名称中文

    @Column(name = "statistic_date")
    private Date statisticDate;  // 统计日期("yyyy-mm-dd")

    @Column(name = "show_type")
    private String showType;  // 展示周期类型(与focus_disease表中show_type一致)

    @Column(name = "period_start")
    private Date periodStart;  // 统计时间段开始时间

    @Column(name = "period_end")
    private Date periodEnd;  // 统计时间段结束时间

    @Column(name = "confirm_total")
    private Integer confirmTotal = 0;  // 截止periodEnd总确诊人数

    @Column(name = "confirm_add")
    private Integer confirmAdd = 0;  // 时间段内新增确诊

    @Column(name = "death_total")
    private Integer deathTotal = 0;  // 截止periodEnd总死亡人数

    @Column(name = "death_add")
    private Integer deathAdd = 0;  // 时间段内新增死亡

    @Column(name = "cure_total")
    private Integer cureTotal = 0;  // 截止periodEnd总治愈人数

    @Column(name = "cure_add")
    private Integer cureAdd = 0;  // 时间段内新增治愈
}
