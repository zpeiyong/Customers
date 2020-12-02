package com.dataint.service.datapack.model.vo;

import com.dataint.service.datapack.db.entity.StatisticBasic;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 基本统计数据(BI大屏使用)
 */
@Data
@NoArgsConstructor
public class StatisticBasicBIVO {

    public StatisticBasicBIVO(StatisticBasic statisticBasic) {
        if (statisticBasic != null)
            BeanUtils.copyProperties(statisticBasic, this);
    }

    private Long diseaseId;  // 传染病id(暂对应focus_disease表)

    private Date statisticDate;  // 统计日期

    private Integer countryTotal = 0;  // 传播国家数量-总数

    private Integer countryAdd = 0;  // 传播国家数量-增加

    private Integer eventTotal = 0;  // 事件数量-总数

    private Integer eventAdd = 0;  // 事件数量-增加

    private Integer articleTotal = 0;  // 舆情数量-总数

    private Integer articleAdd = 0;  // 舆情数量-新增
}
