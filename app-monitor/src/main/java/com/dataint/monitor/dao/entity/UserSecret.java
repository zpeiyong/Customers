package com.dataint.monitor.dao.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name ="user_secret" )
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSecret extends BasePO {

    public String name;  // 保密等级名称

    public String code;  // 保密等级code(暂与name一致)

    public String description; // 保密等级描述
}
