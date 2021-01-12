package com.dataint.service.datapack.db.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 关注传染病表
 */
@Entity
@Table(name = "focus_diseases")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FocusDisease extends BasePO {

    @Column(name = "code", nullable = false)
    private String code;  // 国际疾病伤害及死因分类标准第十版code

    @Column(name = "name_cn", nullable = false)
    private String nameCn;  // 传染病中文

    @Column(name = "name_en", nullable = false)
    private String nameEn;  // 传染病英文

    @Column(name = "name_cn_first")
    private String nameCnFirst;  // 传染病中文首字母(供搜索)

    @Column(name = "if_popular")
    private Boolean ifPopular;  // 是否为热门关注(展示顺序在前)

    @Column(name = "show_type")
    private String showType;  // 展示周期类型(daily,weekly,monthly,quarterly,yearly)-影响bi上最新数据相关时间区间

    @Column(name = "status")
    private Integer status;  // 状态(1:可用; 0:不可用)
}
