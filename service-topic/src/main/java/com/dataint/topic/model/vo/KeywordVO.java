package com.dataint.topic.model.vo;

import com.dataint.topic.db.entity.Keywords;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class KeywordVO {

    public KeywordVO(Keywords keywords, Long topicId) {
        BeanUtils.copyProperties(keywords, this);
        this.topicId = topicId;
    }

    private Long id;

    private Long topicId;

    private String name; // 关键词

    private Boolean enable = true;  // 是否可用(只对爬虫脚本过滤需要爬取的关键词有作用)
}
