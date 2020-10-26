package com.dataint.topic.model.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventBaseVO {

    private Long articleId;  // TopicArticle.articleId

    private String subTitle;  // Topic.subTitle

    private String eventType;  // Topic.eventType
}
