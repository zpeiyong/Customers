package com.dataint.service.datapack.dao.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "country")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country extends BasePO {

    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @Column(name = "name_cn", nullable = false, length = 100)
    private String nameCn;

    @Column(name = "name_en", length = 200)
    private String nameEn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;  // 大洲信息

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @Column(name = "status")
    private Integer status;  // 状态(1:可用; 0:不可用)
}
