package com.dataint.topic.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpiderProject {

    private String group;

    private String name;

    private String status;

    private Long updateTime;

    private Float rate;

    private Float burst;

    private String comments;
}
