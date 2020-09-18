package com.dataint.topic.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 专题组表
 */

@Entity
@Table ( name ="topic" )
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topic  {
    private static final String SYSTEM = "system";
    private static final long serialVersionUID =  6821295278083265413L;
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

    @Column(name = "name")
    private String name; //专题组名称

    @Column(name = "enable", nullable = false)
    private Boolean enable = true;  // 是否可用

    @Column(name="if_deleted")
    private Boolean ifDeleted = false; //是否已删除(1:已删除;0:未删除)

    @Transient
    private List<String> keyWordName;

}
