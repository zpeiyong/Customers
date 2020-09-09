package com.dataint.monitor.dao.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name ="user_type" )
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserType extends BasePO {

    public String type;  // 人员类别(中文)

    public String code;  // 人员类别code(暂与type一致)

    public String description; // 人员类别描述
}
