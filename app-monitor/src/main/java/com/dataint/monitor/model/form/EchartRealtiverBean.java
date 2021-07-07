package com.dataint.monitor.model.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@ApiModel
@Data
public class EchartRealtiverBean {

    private List<EchartRealtivevNodeBean> nodes;

    private List<EchartRealtiverLinkBean> links;


}
