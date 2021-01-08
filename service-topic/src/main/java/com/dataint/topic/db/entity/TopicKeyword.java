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
 * 专题关键词关系表
 */

@Entity
@Table ( name ="topic_keyword_copy1" )
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicKeyword extends BasePO {

    @Column(name="topic_id")
    private Long topicId; // 所属专题组id

    @Column(name="keyword_id")
    private Long keywordId; // 关键词id

}
