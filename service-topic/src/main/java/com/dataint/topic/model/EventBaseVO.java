package com.dataint.topic.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventBaseVO {

    private Integer articleId;  // TopicArticle.articleId

    private String subTitle;  // Topic.subTitle

    private String eventType;  // Topic.eventType
}
