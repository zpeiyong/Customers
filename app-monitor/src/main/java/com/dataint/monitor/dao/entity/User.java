package com.dataint.monitor.dao.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Set;

@Entity
@Table ( name ="user" )
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BasePO {
    private static final long serialVersionUID =  6821295278083265413L;

    @Column(name = "username", nullable = false)
    private String username;  // 登录用户名

    @Column(name = "password", nullable = false)
    private String password;  // 登录密码

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "name")
    private String name;  // 人员姓名

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;  // 用户是否可用

    @Column(name = "description")
    private String description;

    @Transient
    private Set<Long> roleIds;
}
