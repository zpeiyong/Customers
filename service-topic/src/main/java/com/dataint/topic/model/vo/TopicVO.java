package com.dataint.topic.model.vo;

import com.dataint.topic.db.entity.Topic;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class TopicVO {

    private Long id;

    private String name;

    private boolean enable;

    private boolean ifdeleted;

    private Date createdTime;

    private Date updatedTime;

    private List<String> keyWordNames;

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