package com.dataint.topic.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 专题关键词表
 */

@Entity
@Table ( name ="topic_keyword" )
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicKeyword  {
    private static final String SYSTEM = "system";
    private static final long serialVersionUID =  6821295278083265413L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; //专题关键词-主键id

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

    @Column(name="name")
    private String name; //关键词

    @Column(name = "enable", nullable = false)
    private Boolean enable = true;  // 是否可用

    @Column(name="topic_id")
    private Long topicId; // 所属专题组id
}
