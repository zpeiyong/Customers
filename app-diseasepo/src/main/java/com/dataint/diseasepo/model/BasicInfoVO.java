package com.dataint.diseasepo.model;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.diseasepo.dao.entity.StatisticBasic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicInfoVO extends BaseVO {

    public BasicInfoVO(StatisticBasic statisticBasic) {
        BeanUtils.copyProperties(statisticBasic, this);
    }

    private Integer diseaseTotalCnt;  // 疫情总数

    private Integer typeYearlyCnt;  // 今年爆发疫情类别种数

    private Integer countryYearlyCnt;  // 今年疫情爆发国家(地区)个数

    private Integer caseYearlyCnt;  // 今年病例个数

    private Integer deathYearlyCnt;  // 今年死亡人数

    private Integer diseaseDailyCnt;  // 今日疫情数

    private Integer typeDailyCnt;  // 今日疫情类别种数

    private Integer countryDailyCnt;  // 今日疫情爆发国家(地区)个数

}
