package com.dataint.service.datapack.db.entity;

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
@Deprecated  //
public class Diseases extends BasePO {

    @Column(name = "icd10_code")
    private String icd10Code;  // 国际疾病伤害及死因分类标准第十版code

    @Column(name = "name_cn", nullable = false)
    private String nameCn;  // 传染病中文

    @Column(name = "name_en", nullable = false)
    private String nameEn;  // 传染病英文

    @Column(name = "name_cn_first")
    private String nameCnFirst;  // 传染病中文首字母(供搜索)

    @Column(name = "type_id")
    private Integer typeId;  // 传染病类型表(暂无)id

    @Column(name = "type_name_cn")
    private String typeNameCn;  // 传染病类别(暂无)中文名称

    @Column(name = "level")
    private Integer level;  // 传染病级别

    @Column(name = "status")
    private Integer status;  // 状态(1:可用; 0:不可用)

    @Column(name = "alias_cn")
    private String alias;
}
