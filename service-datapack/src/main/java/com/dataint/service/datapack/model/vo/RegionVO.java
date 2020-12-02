package com.dataint.service.datapack.model.vo;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.service.datapack.db.entity.Region;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class RegionVO extends BaseVO {

    public RegionVO(Region region) {
        BeanUtils.copyProperties(region, this);
    }

    private String nameCn;

    private String code;

    private Integer sort;
}
