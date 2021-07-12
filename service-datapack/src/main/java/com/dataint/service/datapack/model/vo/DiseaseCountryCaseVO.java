package com.dataint.service.datapack.model.vo;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.service.datapack.db.entity.DiseaseCountryCase;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@NoArgsConstructor
public class DiseaseCountryCaseVO extends BaseVO {

    public DiseaseCountryCaseVO(DiseaseCountryCase diseaseCountryCase) {
        BeanUtils.copyProperties(diseaseCountryCase, this);
    }

    public DiseaseCountryCaseVO(DiseaseCountryCase diseaseCountryCase, String diseaseNameCn, String countryNameCn) {
        BeanUtils.copyProperties(diseaseCountryCase, this);
        this.setDiseaseNameCn(diseaseNameCn);
        this.setCountryNameCn(countryNameCn);
    }

    private Long diseaseId;  // 传染病id(暂对应focus_disease表)

    private String diseaseNameCn;  // 传染病名称中文

    private Long countryId;  // 国家id

    private String countryNameCn;  // 国家名称中文

    private Date statisticDate;  // 统计日期("yyyy-mm-dd")

    private String showType;  // 展示周期类型(与focus_disease表中show_type一致)

    private Date periodStart;  // 统计时间段开始时间

    private Date periodEnd;  // 统计时间段结束时间

    private Integer confirmTotal;  // 截止periodEnd总确诊人数

    private Integer confirmAdd;  // 时间段内新增确诊

    private Integer deathTotal;  // 截止periodEnd总死亡人数

    private Integer deathAdd;  // 时间段内新增死亡

    private Integer cureTotal;  // 截止periodEnd总治愈人数

    private Integer cureAdd;  // 时间段内新增治愈

    private int week; //周数

    private float chainComparison; //环比

    private float yearComparison; //同比

    private float chainDeathComparison; //死亡 环比

    private float yearDeathComparison; //死亡同比
}
