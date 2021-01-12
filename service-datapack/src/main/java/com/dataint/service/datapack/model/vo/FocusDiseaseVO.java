package com.dataint.service.datapack.model.vo;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.cloud.common.utils.DateUtil;
import com.dataint.service.datapack.db.entity.FocusDisease;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class FocusDiseaseVO extends BaseVO {
    public FocusDiseaseVO(FocusDisease focusDisease) {
        BeanUtils.copyProperties(focusDisease, this);

        if ("daily".equals(showType)) {
            firstDateOfPeriod = DateUtil.getTodayStart().split(" ")[0];
        } else if ("weekly".equals(showType)) {
            firstDateOfPeriod = DateUtil.getWeekStart().split(" ")[0];
        } else if ("monthly".equals(showType)) {
            firstDateOfPeriod = DateUtil.getStartTimeOfMonth().split(" ")[0];
        } else if ("quarterly".equals(showType)) {
            firstDateOfPeriod = DateUtil.getStartTimeOfQuarter().split(" ")[0];
        } else if ("yearly".equals(showType)) {
            firstDateOfPeriod = DateUtil.getStartTimeOfYearly().split(" ")[0];
        }
    }

    private String nameCn;

    private String nameEn;

    private String showType;  // 展示周期类型

    private String firstDateOfPeriod;  // 当前周期第一天日期

    private Integer status;  // 状态(1:可用; 0:不可用)
}
