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
 * 病种国家舆情数统计表(每天)
 */
@Entity
@Table(name = "disease_country_po")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseCountryPO extends BasePO {

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

    @Column(name = "risk_score")
    private Double riskScore;  // 风险值

    @Column(name = "event_total")
    private Integer eventTotal = 0;  // 截止statisticDate结束事件数量-总数

    @Column(name = "event_add")
    private Integer eventAdd = 0;  // 当天事件数量-增加

    @Column(name = "article_total")
    private Integer articleTotal = 0;  // 截止statisticDate结束舆情数量-总数

    @Column(name = "article_add_media")
    private Integer articleAddMedia = 0;  // 当天舆情数量(新闻媒体)-新增

    @Column(name = "article_add_official")
    private Integer articleAddOfficial = 0;  // 当天舆情数量(官方发布)-新增

}
