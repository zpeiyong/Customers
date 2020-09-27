package com.dataint.topic.db.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Entity
@Table(name = "media")
@Accessors(chain = true)
public class Media {
    public Media() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_id", nullable = false)
    private Integer mediaId;

    @Column(name = "media_name", nullable = false)
    private String mediaName;

    @Column(name = "media_type_id")
    private Integer mediaTypeId;

}
