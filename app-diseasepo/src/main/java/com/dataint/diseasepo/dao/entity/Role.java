package com.dataint.diseasepo.dao.entity;

import com.dataint.cloud.common.model.po.BasePO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table( name ="role" )
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private static final String SYSTEM = "system";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "created_by")
    private String createdBy = SYSTEM;

    @Column(name = "created_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdTime = new Date();

    @Column(name = "updated_by")
    private String updatedBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_time")
    private Date updatedTime;

    @Column(name="code")
    private String code;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;
}
