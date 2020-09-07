package com.dataint.cloud.common.model.param;

import lombok.Data;

import java.util.Date;

@Data
public class BaseParam extends PageParam {
    private Date createdTimeStart;
    private Date createdTimeEnd;
}
