package com.dataint.topic.db.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 申请表
 */

@Entity
@Table ( name ="application" )
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application extends BasePO {

    @Column(name = "from_page")
    private String fromPage; // 申请页面(暂时只有"专题")

    @Column(name="topic_id", nullable = false, columnDefinition = "bigint(20) default 0")
    private Integer topicId = 0;  // 专题id

    @Column(name = "topic_name", nullable = false)
    private String topicName;

    @Column(name = "operation", nullable = false)
    private String operation;  // 申请操作(add,update,delete)

    @Column(name = "keywords")
    private String keywords;  // 关键词列表(多个关键词根据|区分)

    @Column(name = "status", nullable = false, columnDefinition ="tinyint(1) default 0")
    private Integer status =0;  // 状态(0:未处理1:通过;2:拒绝)

    @Column(name = "feedback")
    private String feedback;  // 反馈内容

    @Column(name = "update_desc")
    private String updateDesc; //修改说明

}
