package com.dataint.service.datapack.model.param;

import com.dataint.cloud.common.model.param.PageParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FocusDiseaseParam extends PageParam {
    private  Long  id;
    private String nameCn;

    private String nameEn;

    private String showType;  // 展示周期类型

    private String firstDateOfPeriod;  // 当前周期第一天日期

    private Integer status;  // 状态(1:可用; 0:不可用)
    private  Boolean ifPopular;
}
