package com.dataint.monitor.model.param;

import com.dataint.cloud.common.model.param.PageParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleListQueryParam extends PageParam {

    private String keyword;  // 关键词

    private Long diseaseId;  // 疫情类型id

    private Long mediaTypeId;  // 来源媒体类型id

    private String crawlTimeStart;  // 爬取时间start

    private String crawlTimeEnd;  // 爬取时间end

    private String articleType;  // 舆情文章内容类型

    private Long regionId;  // 大洲id(直航地区id=0)
}
