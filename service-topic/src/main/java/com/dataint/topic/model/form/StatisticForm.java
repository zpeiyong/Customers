package com.dataint.topic.model.form;

import com.dataint.cloud.common.model.param.PageParam;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class StatisticForm extends PageParam implements Serializable {

    private Integer keywordId;

    private Date startTime;

    private Date endTime;

}
