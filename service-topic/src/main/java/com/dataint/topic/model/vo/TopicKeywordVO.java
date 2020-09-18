package com.dataint.topic.model.vo;

import com.dataint.topic.db.entity.TopicKeyword;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

/**
 * 专题主键表
 *
 */

@Data
@NoArgsConstructor
public class TopicKeywordVO {
    private Long id;//关键词主键id

    private Long topicId;//专题组id

    private boolean enable;//是否可用

    private String name;//关键词

    private String topicName;//专题组名

    private Date createdTime;

    private Date updatedTime;

    private ArrayList<TopicTreeVO> children;

    public TopicKeywordVO(TopicKeyword topicKeyword){
        this.setId(topicKeyword.getId());
        this.setTopicId(topicKeyword.getTopicId());
        this.setEnable(topicKeyword.getEnable());
        this.setName(topicKeyword.getName());
        this.setCreatedTime(topicKeyword.getCreatedTime());
        this.setUpdatedTime(topicKeyword.getUpdatedTime());

    }

}
