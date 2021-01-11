package com.dataint.monitor.model;

import com.dataint.cloud.common.model.BaseVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class ArticleOrigBasicVO extends BaseVO {

    private String origTitle;

    private String origSummary;

    private String origContent;

    private List<String> origKeywordList;

}
