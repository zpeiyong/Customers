package com.dataint.topic.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 媒体表
 */

@Entity
@Table(name = "media")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Media implements Serializable {

    private static final long serialVersionUID = 8389944383582836173L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name_cn")
    private String nameCn;  // 媒体中文名称

    @Column(name = "name_en")
    private String nameEn;  // 媒体英文名称

    @Column(name = "media_type_id")
    private Long mediaTypeId;  // 媒体类型id

}
