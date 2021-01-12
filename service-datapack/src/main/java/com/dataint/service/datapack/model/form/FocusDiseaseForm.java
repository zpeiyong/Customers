package com.dataint.service.datapack.model.form;

import com.dataint.cloud.common.model.form.BaseForm;

import com.dataint.service.datapack.db.entity.FocusDisease;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel
@Data
public class FocusDiseaseForm extends BaseForm<FocusDisease> {

    private  Long  id;
    @NotBlank
    private String nameEn;
    @NotBlank
    private String nameCn;

    @NotBlank
    private String showType;  // 展示周期类型

    private  String   nameCnFirst;

    private Integer status;  // 状态(1:可用; 0:不可用)

    private  Boolean ifPopular;
}
