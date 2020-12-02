package com.dataint.service.datapack.model.vo;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.service.datapack.db.entity.Event;
import com.dataint.service.datapack.utils.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class EventVO extends BaseVO {

    public EventVO(Event event) {
        BeanUtils.copyProperties(event, this);

        if (StringUtils.isNotEmpty(event.getEventStatus()))
            this.eventStatus = Constants.eventStatusMap.get(event.getEventStatus());
    }

    private String eventName;  // 疫情事件名称

    private Integer diseaseId;  // 疫情类型表主键id

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date eventStart;  // 疫情事件开始时间

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date eventEnd;  // 疫情事件结束时间

    private String eventStatus;  // 疫情事件状态(默认:监测提交)

    private String enabled;  // 疫情事件是否启动

    private List<SourceVO> sourceVOList;  // 数据来源列表
}
