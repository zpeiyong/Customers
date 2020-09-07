package com.dataint.diseasepo.model;

import com.dataint.diseasepo.dao.entity.ImpactRank;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class ImpactRankVO {

    private Integer diseaseId;  // 传染病表主键Id

    private String diseaseName;  // 传染病名称

    private String affectedArea;  // 国家名称(英文字符‘逗号’分隔)

    private String burstTime;  // 爆发时间(“2019-01”)

    private Integer rank;  // 排行顺序

    public ImpactRankVO() {}

    public ImpactRankVO(ImpactRank impactRank) {
        BeanUtils.copyProperties(impactRank, this);
    }
}
