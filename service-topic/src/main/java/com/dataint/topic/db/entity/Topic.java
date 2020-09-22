package com.dataint.topic.db.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 专题组表
 */

@Entity
@Table ( name ="topic" )
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topic extends BasePO {

    @Column(name = "name")
    private String name; // 专题组名称

    @Column(name = "enable", nullable = false)
    private Boolean enable = true;  // 是否可用

    @Column(name="if_deleted")
    private Boolean ifDeleted = false; //是否已删除(1:已删除;0:未删除)

    @Transient
    private List<String> keywordList;

}
