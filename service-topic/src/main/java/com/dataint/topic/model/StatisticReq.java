package com.dataint.topic.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class StatisticReq extends PageParam implements Serializable {

    private Integer keywordId;

    private Date startTime;

    private Date endTime;

}
