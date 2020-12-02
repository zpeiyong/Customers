package com.dataint.service.datapack.model.vo;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.service.datapack.db.entity.Warning;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 预警信息列表VO
 */
@Data
@NoArgsConstructor
public class WarningVO extends BaseVO {

    public WarningVO(Warning warning) {
        BeanUtils.copyProperties(warning, this);
    }

    private String title;  // 预警标题

    private Integer level;  // 预警等级

    private Boolean enable;  // 是否有效(true:有效,false:无效)

    private String reason;  // 预警原因

    private Date createdTime;  // 创建预警时间
}
