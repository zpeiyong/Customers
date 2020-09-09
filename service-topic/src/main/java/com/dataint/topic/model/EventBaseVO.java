package com.dataint.topic.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventBaseVO {

    private Integer articleId;  // Article.articleId

    private String subTitle;  // Event.subTitle

    private String eventType;  // Event.eventType
}
