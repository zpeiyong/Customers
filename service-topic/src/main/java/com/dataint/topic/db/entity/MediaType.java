package com.dataint.topic.db.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "media_type")
@Accessors(chain = true)
public class MediaType implements Serializable {
    public MediaType() {}
//
//    public MediaType(Integer mediaTypeId) {
//        this.mediaTypeId = mediaTypeId;
//    }
//
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_type_id", nullable = false)
    private Integer mediaTypeId;

    @Column(name = "media_type_name", nullable = false)
    private String mediaTypeName;

}
