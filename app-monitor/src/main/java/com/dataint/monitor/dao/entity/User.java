package com.dataint.monitor.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table ( name ="user" )
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User  {
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

    @Column(name = "group_id")
    private Long groupId;  // 用户所在组织主键id

    @Column(name = "username", nullable = false)
    private String username;  // 登录用户名

    @Column(name = "password", nullable = false)
    private String password;  // 登录密码

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "name")
    private String name;  // 人员姓名

    @Column(name = "type")
    private String type;  // 人员类别

    @Column(name = "secret_level")
    private String secretLevel;  // 密级

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;  // 用户是否可用

    @Column(name = "description")
    private String description;

    @Transient
    private Set<Long> roleIds;
}
