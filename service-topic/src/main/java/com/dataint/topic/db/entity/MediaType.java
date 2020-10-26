package com.dataint.topic.db.entity;

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

    private static final long serialVersionUID = -1138614895756363492L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;  // 媒体类型表主键id

    @Column(name = "name")
    private String name;  // 媒体类型名称

}
