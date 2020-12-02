package com.dataint.monitor.model;

import com.dataint.cloud.common.model.BaseVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationVO extends BaseVO {

    private String fromPage; // 申请页面(暂时只有"专题")

    private Integer topicId = 0;  // 专题id

    private String topicName;

    private String operation;  // 申请操作(add,update,delete)

    private String keywords;  // 关键词列表(多个关键词根据|区分)

    private Integer status =0;  // 状态(0:未处理1:通过;2:拒绝)

    private String feedback;  // 反馈内容

    private String updateDesc; //修改说明

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdTime;;//申请时间

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;;//更新时间
}
