package com.dataint.service.datapack.model;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.service.datapack.dao.entity.Source;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class SourceVO extends BaseVO {

    public SourceVO(Source source) {
        BeanUtils.copyProperties(source, this);

        //
        if (source.getCountry() != null) {
            this.countryId = source.getCountry().getId();
            this.countryName = source.getCountry().getNameCn();
        }
    }

    private Integer countryId;

    private String countryName;

    private String description;

    private String sourceUrl;

    private String sourceStatus;  // 疫情事件数据来源状态
}
