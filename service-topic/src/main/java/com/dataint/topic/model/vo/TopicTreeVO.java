package com.dataint.topic.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class TopicTreeVO {
    private Long id;
    private String name;
    private Date createTime;
}
