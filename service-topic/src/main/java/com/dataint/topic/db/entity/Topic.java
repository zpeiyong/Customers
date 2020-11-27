package com.dataint.topic.db.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
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

    @Column(name = "name", unique = true)
    private String name; // 专题组名称

    @Column(name="if_deleted", nullable = false)
    private Boolean ifDeleted = false;  // 是否已删除(1:已删除;0:未删除)

    @Column(name = "if_recommend", nullable = false)
    private Boolean ifRecommend = false;  // 是否为推荐专题

    @Column(name = "if_watch", nullable = false)
    private Boolean ifWatch = false;  // 是否设置关注

    @Column(name = "enable", nullable = false)
    private Boolean enable = true;  // 是否可用

    @Transient
    private List<String> keywordList;

}
