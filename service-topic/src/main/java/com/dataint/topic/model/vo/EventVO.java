package com.dataint.topic.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EventVO extends EventBaseVO {

    private Integer keywordId;  // PoKeyword.keywordId

    private Date eventTime;

    private Integer mediaTypeId;  // Media.mediaTypeId

    private String sourceName;

    private String title;

    private String summary;

}
