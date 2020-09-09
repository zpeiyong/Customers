package com.dataint.monitor.dao.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "map_basic")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapBasic extends BasePO {

    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "disease_yearly_cnt")
    private Integer diseaseYearlyCnt;

    @Column(name = "latest", columnDefinition = "varchar(1) default 'Y'")
    private String latest = "Y";  // 是否可用(Y:是最新数据; N:历史数据)

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "statistic_map_id")
    private List<MapDisease> detailList;  //  国家对应疫情统计信息
}
