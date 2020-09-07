package com.dataint.service.datapack.dao.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "sources")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Source extends BasePO {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;  // 国家

    @Column(name = "description")
    private String description;

    @Column(name = "source_url")
    private String sourceUrl;

    @Column(name = "source_status")
    private String sourceStatus;  // 疫情事件数据来源状态

}
