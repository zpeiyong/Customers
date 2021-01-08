package com.dataint.topic.model.vo;

import com.dataint.topic.db.entity.Topic;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TopicVO {

    private Long id;

    private String name;

    private boolean enable;

    private boolean ifDeleted;

    private Date createdTime;

    private Date updatedTime;

    private List<String> keywordNames;

    private ArrayList<TopicTreeVO> children;

    public TopicVO(Topic topic) {
        BeanUtils.copyProperties(topic, this);
    }

//    public TopicVO(Topic topic,){
//        this.setId(topic.getId());
//        this.setName(topic.getName());
//        this.setEnable(topic.getEnable());
//        this.setIfdeleted(topic.getIfDeleted());
//        this.setCreatedTime(topic.getCreatedTime());
//        this.setUpdatedTime(topic.getUpdatedTime());
//    }

}
