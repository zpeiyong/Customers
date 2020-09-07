package com.dataint.diseasepo.model.param;

import com.dataint.cloud.common.model.param.BaseParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventQueryParam extends BaseParam {
    private String keyword;
    private Integer diseaseId;
}
