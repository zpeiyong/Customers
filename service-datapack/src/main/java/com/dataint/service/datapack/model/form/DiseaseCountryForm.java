package com.dataint.service.datapack.model.form;

import com.dataint.cloud.common.model.form.BaseForm;
import com.dataint.service.datapack.db.entity.DiseaseCountryCase;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel
@Data
public class DiseaseCountryForm extends BaseForm<DiseaseCountryCase> {

    private  Long  id;
    @NotNull
    private Long diseaseId;  // 传染病id(暂对应focus_disease表)

    @NotBlank
    private String diseaseNameCn;  // 传染病名称中文

    @NotNull
    private Long countryId;  // 国家id

    @NotBlank
    private String countryNameCn;  // 国家名称中文

    private Date statisticDate;  // 统计日期("yyyy-mm-dd")

    @NotBlank
    private String showType;  // 展示周期类型(与focus_disease表中show_type一致)

    @NotBlank
    private Date periodStart;  // 统计时间段开始时间

    private Date periodEnd;  // 统计时间段结束时间

    private Integer confirmTotal;  // 截止periodEnd总确诊人数

    private Integer confirmAdd;  // 时间段内新增确诊

    private Integer deathTotal;  // 截止periodEnd总死亡人数

    private Integer deathAdd;  // 时间段内新增死亡

    private Integer cureTotal;  // 截止periodEnd总治愈人数

    private Integer cureAdd;  // 时间段内新增治愈
}
