package com.dataint.service.datapack.model.vo;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.service.datapack.db.entity.ArticleDisease;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@NoArgsConstructor
public class ArticleDiseaseVO extends BaseVO {

    public ArticleDiseaseVO(ArticleDisease articleDisease) {
        BeanUtils.copyProperties(articleDisease, this);
    }

    private Long articleId;

    private Long diseaseId;

    private String diseaseCode;

    private Long countryId;

    private String countryCode;

    private Date diseaseStart;

    private Date diseaseEnd;

    private Integer periodConfirm;  // 当前时间段新增人数

    private Integer periodDeath;  // 当前时间段死亡人数

    private Integer periodCure;  // 当前时间段治愈人数

    private Integer confirmCases;  // 总确诊人数

    private Integer deathCases;  // 总死亡人数

    private Integer cureCases;  // 总治愈人数

}
