package com.dataint.service.datapack.db.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "events")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event extends BasePO {

    @Column(name = "event_name")
    private String eventName;  // 疫情事件名称

    @Column(name = "disease_id")
    private Integer diseaseId;  // 疫情类型表主键id

    @Column(name = "event_start")
    private Date eventStart;  // 疫情事件开始时间

    @Column(name = "event_end")
    private Date eventEnd;  // 疫情事件结束时间

    @Column(name = "event_status")
    private String eventStatus = "SUBMITTED";  // 疫情事件状态(默认:监测提交)

    @Column(name = "deleted", nullable = false, columnDefinition = "char(1) default 'N'")
    private String deleted = "N";  // 是否已删除数据(N:未删除; Y:已删除)

    @Column(name = "enabled", nullable = false, columnDefinition = "char(1) default 'Y'")
    private String enabled = "Y";
}
