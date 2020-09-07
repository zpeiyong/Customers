package com.dataint.service.datapack.dao.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "diseases")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diseases extends BasePO {

    @Column(name = "icd10_code")
    private String icd10Code;

    @Column(name = "name_cn", nullable = false)
    private String nameCn;

    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @Column(name = "name_cn_first")
    private String nameCnFirst;

    @Column(name = "type_id")
    private Integer typeId;  // 传染病类型表(暂无)id

    @Column(name = "type_name_cn")
    private String typeNameCn;  // 传染病类别(暂无)中文名称

    @Column(name = "level")
    private Integer level;  // 传染病级别

    @Column(name = "status")
    private Integer status;  // 状态(1:可用; 0:不可用)
}
