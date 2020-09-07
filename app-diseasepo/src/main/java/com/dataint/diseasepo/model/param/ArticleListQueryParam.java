package com.dataint.diseasepo.model.param;

import com.dataint.cloud.common.model.param.PageParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleListQueryParam extends PageParam {

    private  String articleType;
}
