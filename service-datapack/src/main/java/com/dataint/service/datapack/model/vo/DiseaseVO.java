package com.dataint.service.datapack.model.vo;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.service.datapack.db.entity.Diseases;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class DiseaseVO extends BaseVO {
    public DiseaseVO(Diseases diseases) {
        BeanUtils.copyProperties(diseases, this);
    }

    private String icd10Code;

    private String nameCn;

    private String nameEn;

    private Integer level;  // 传染病级别

    private Integer status;  // 状态(1:可用; 0:不可用)
}
