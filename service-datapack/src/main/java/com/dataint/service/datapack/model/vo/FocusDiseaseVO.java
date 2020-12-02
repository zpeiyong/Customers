package com.dataint.service.datapack.model.vo;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.service.datapack.db.entity.FocusDisease;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class FocusDiseaseVO extends BaseVO {
    public FocusDiseaseVO(FocusDisease focusDisease) {
        BeanUtils.copyProperties(focusDisease, this);
    }

    private String nameCn;

    private String nameEn;

    private Integer status;  // 状态(1:可用; 0:不可用)
}
