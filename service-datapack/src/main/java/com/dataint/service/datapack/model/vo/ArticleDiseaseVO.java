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

//        // 分隔多个countryId为List
//        if (!StringUtils.isEmpty(articleDisease.getCountryIds())) {
//            this.countryIdList = Arrays.stream(articleDisease.getCountryIds().split(Constants.SPLITTER))
//                    .map(Integer::valueOf).collect(Collectors.toList());
//        }
//        // 分隔多个countryCode为List
//        if (!StringUtils.isEmpty(articleDisease.getCountryCodes())) {
//            this.countryCodeList = Arrays.stream(articleDisease.getCountryCodes().split(Constants.SPLITTER))
//                    .collect(Collectors.toList());
//        }
    }

    private Integer articleId;

    private Integer diseaseId;

    private String diseaseCode;

//    private List<Long> countryIdList;
//
//    private List<String> countryCodeList;

    private Long countryId;

    private String countryCode;

    private Date diseaseStart;

    private Date diseaseEnd;

    private Integer newCases;  //新增病例

    private Integer cumulativeCases;    //累计病例

    private Integer confirmedCases;     //确诊病例

    private Integer suspectedCases;     //疑似病例

    private Integer deathToll; //死亡病例
}
