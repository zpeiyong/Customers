package com.dataint.monitor.dao.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "impact_rank")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImpactRank extends BasePO {

    @Column(name = "disease_id")
    private Integer diseaseId;  // 传染病表主键Id

    @Column(name = "disease_name")
    private String diseaseName;  // 传染病名称

    @Column(name = "affected_area")
    private String affectedArea;  // 主要波及国家名称(英文符号逗号分隔)

    @Column(name = "burst_time")
    private String burstTime;  // 爆发时间(年-月)

    @Column(name = "rank")
    private Integer rank;  // 传染病在排行榜顺序

    @Column(name = "latest", columnDefinition = "varchar(1) default 'Y'")
    private String latest = "Y";  // 是否可用(Y:是最新数据; N:历史数据)

}
