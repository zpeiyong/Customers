package com.dataint.monitor.model.param;

import com.dataint.cloud.common.model.param.PageParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiseaseQueryParam extends PageParam {

    private String keyword;  // 关键词
}
