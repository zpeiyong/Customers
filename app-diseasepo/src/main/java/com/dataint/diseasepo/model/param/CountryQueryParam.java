package com.dataint.diseasepo.model.param;

import com.dataint.cloud.common.model.param.PageParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryQueryParam extends PageParam {

    private String keyword;  // 关键词
}
