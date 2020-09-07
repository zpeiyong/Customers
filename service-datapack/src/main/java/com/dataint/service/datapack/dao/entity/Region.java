package com.dataint.service.datapack.dao.entity;

import com.dataint.cloud.common.model.po.BasePO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "region")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Region extends BasePO {

    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @Column(name = "name_cn", nullable = false, length = 100)
    private String nameCn;

    @Column(name = "name_en", length = 200)
    private String nameEn;

}
