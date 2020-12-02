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
@Table(name = "site")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Site extends BasePO {

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "name_cn", nullable = false)
    private String nameCn;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "name_origin")
    private String nameOrigin;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "language")
    private String language;

    @Column(name = "site_type")
    private String siteType = "1";  // 网站类型(1-官方;2-第三方)
}
