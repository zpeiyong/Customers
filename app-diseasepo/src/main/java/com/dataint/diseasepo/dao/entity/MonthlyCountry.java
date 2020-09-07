package com.dataint.diseasepo.dao.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "monthly_country")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyCountry extends BasePO {

    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "disease_id")
    private Integer diseaseId;  // 传染病表主键Id

    @Column(name = "disease_name")
    private String diseaseName;  // 传染病名称

    @Column(name = "months")
    private String months;  // 主要影响月份

    @Column(name = "disease_sort")
    private Integer diseaseSort;  // 传染病在当前国家排序

    @Column(name = "latest", columnDefinition = "varchar(1) default 'Y'")
    private String latest = "Y";  // 是否可用(Y:是最新数据; N:历史数据)

}
