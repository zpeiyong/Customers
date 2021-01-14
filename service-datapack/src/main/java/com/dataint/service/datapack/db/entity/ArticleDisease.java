package com.dataint.service.datapack.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "article_disease")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDisease implements Serializable {

    private static final long serialVersionUID = 5368673163993359651L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;  // 舆情传染病信息表-主键id

    @Column(name = "article_id")
    private Long articleId;  // Article表id

    @Column(name = "disease_id")
    private Long diseaseId;  // 传染病表主键id
    
    @Column(name = "disease_code")
    private String diseaseCode;  // 传染病编码

    @Column(name = "country_id")
    private Long countryId;  // 国家表主键id

    @Column(name = "country_code")
    private String countryCode;  // 国家编码

    @Column(name = "disease_start")
    private Date diseaseStart;  // 疫情开始时间(若无disease_end则表示时间点)

    @Column(name = "disease_end")
    private Date diseaseEnd;  // 疫情结束时间

    @Column(name = "period_confirm")
    private Integer periodConfirm;  // 当前时间段新增人数

    @Column(name = "period_death")
    private Integer periodDeath;  // 当前时间段死亡人数

    @Column(name = "period_cure")
    private Integer periodCure;  // 当前时间段治愈人数

    @Column(name = "confirm_cases")
    private Integer confirmCases;  // 总确诊人数

    @Column(name = "death_cases")
    private Integer deathCases;  // 总死亡人数

    @Column(name = "cure_cases")
    private Integer cureCases;  // 总治愈人数

}
