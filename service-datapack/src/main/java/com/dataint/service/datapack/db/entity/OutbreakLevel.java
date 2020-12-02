package com.dataint.service.datapack.db.entity;

import com.dataint.cloud.common.model.po.BasePO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
//@Table(name = "outbreak_level")
//@Accessors(chain = true)
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
//public class OutbreakLevel extends BasePO {
//
//    @Column(name = "description")
//    private String description;  // 舆情等级描述
//
//    @Column(name = "level_code")
//    private String levelCode = "1";  // 疫情等级编码(默认一般:1)
//
//}
