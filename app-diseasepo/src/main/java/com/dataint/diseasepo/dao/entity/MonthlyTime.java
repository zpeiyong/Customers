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
@Table(name = "monthly_time")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyTime extends BasePO {

    @Column(name = "month")
    private String month;

    @Column(name = "disease_id")
    private Integer diseaseId;  // 传染病表主键Id

    @Column(name = "disease_name")
    private String diseaseName;  // 传染病名称

    @Column(name = "country_names")
    private String countryNames;  // 国家名称(英文字符‘逗号’分隔)

    @Column(name = "disease_sort")
    private Integer diseaseSort;  // 传染病在当月排序

    @Column(name = "latest", columnDefinition = "varchar(1) default 'Y'")
    private String latest = "Y";  // 是否最新数据(Y:是最新数据; N:历史数据)

}
