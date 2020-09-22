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
 * 专题关键词表
 */

@Entity
@Table ( name ="topic_keyword" )
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicKeyword extends BasePO {

    @Column(name="name")
    private String name; // 关键词

    @Column(name = "enable", nullable = false)
    private Boolean enable = true;  // 是否可用

    @Column(name="topic_id")
    private Long topicId; // 所属专题组id
}
