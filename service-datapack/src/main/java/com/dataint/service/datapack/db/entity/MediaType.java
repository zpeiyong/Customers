package com.dataint.service.datapack.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 媒体类型表
 */
@Entity
@Table(name = "media_type")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaType implements Serializable {

    private static final long serialVersionUID = 3000115169576051563L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;  // 媒体类型表主键id

    @Column(name = "name")
    private String name;  // 媒体类型名称

    @Column(name = "sort")
    private Integer sort;  // 顺序

}
