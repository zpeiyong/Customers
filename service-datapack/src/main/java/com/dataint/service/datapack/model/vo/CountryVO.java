package com.dataint.service.datapack.model.vo;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.service.datapack.db.entity.Country;
import com.dataint.service.datapack.db.entity.Region;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CountryVO extends BaseVO {

    public CountryVO(Country country) {
        BeanUtils.copyProperties(country, this);
    }

    private String code;

    private String nameCn;

    private String nameEn;

    private Region region;  // 大洲信息

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Integer status;  // 状态(1:可用; 0:不可用)
}
