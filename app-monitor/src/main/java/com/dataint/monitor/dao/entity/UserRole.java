package com.dataint.monitor.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table ( name ="user_role" )
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole implements Serializable {

    private static final long serialVersionUID =  1014026458574210865L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;  // 用户角色表主键id

    @Column(name = "user_id" )
    private Long userId;

    @Column(name = "role_id" )
    private Long roleId;

}
