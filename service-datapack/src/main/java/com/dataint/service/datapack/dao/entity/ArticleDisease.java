package com.dataint.service.datapack.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "article_disease")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDisease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "article_id")
    private Integer articleId;

    @Column(name = "disease_id")
    private Integer diseaseId;
    
    @Column(name = "disease_code")
    private String diseaseCode;

    @Column(name = "country_ids")
    private String countryIds;

    @Column(name = "country_codes")
    private String countryCodes;

    @Column(name = "disease_start")
    private Date diseaseStart;

    @Column(name = "disease_end")
    private Date diseaseEnd;

    @Column(name = "new_cases")
    private Integer newCases;  //新增病例

    @Column(name = "cumulative_cases")
    private Integer cumulativeCases;  //累计病例

    @Column(name = "confirmed_cases")
    private Integer confirmedCases; //确诊病例

    @Column(name = "suspected_cases")
    private Integer suspectedCases; //疑似病例

    @Column(name = "death_toll")
    private Integer deathToll;  //死亡病例
}
