package com.dataint.service.datapack.model.vo;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.service.datapack.db.entity.StatisticBasic;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 基本统计数据(首页使用)
 */
@Data
@NoArgsConstructor
public class StatisticBasicVO extends BaseVO {

    public StatisticBasicVO(StatisticBasic statisticBasic) {
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

    private Integer confirmTotal = 0;  // 总确诊人数

    private Integer confirmAdd = 0;  // 新增确诊

    private Integer deathTotal = 0;  // 总死亡人数

    private Integer deathAdd = 0;  // 新增死亡

    private Integer cureTotal = 0;  // 总治愈人数

    private Integer cureAdd = 0;  // 新增治愈
}
