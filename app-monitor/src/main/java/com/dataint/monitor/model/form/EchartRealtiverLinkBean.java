package com.dataint.monitor.model.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel
@Data
public class EchartRealtiverLinkBean {

    private int source;
    private int target;
}
