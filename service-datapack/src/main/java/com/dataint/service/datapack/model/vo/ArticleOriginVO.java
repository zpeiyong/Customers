package com.dataint.service.datapack.model.vo;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.cloud.common.model.Constants;
import com.dataint.service.datapack.db.entity.ArticleOrigin;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
public class ArticleOriginVO extends BaseVO {

    public ArticleOriginVO(ArticleOrigin articleOrigin) {
        BeanUtils.copyProperties(articleOrigin, this);

        if (!StringUtils.isEmpty(articleOrigin.getOrigKeywords()))
            this.origKeywordList = Arrays.asList(articleOrigin.getOrigKeywords().split(Constants.SPLITTER));
    }

    private Integer articleId;

    private String origTitle;

    private String origAuthor;

    private String origEditor;

    private String origSummary;

    private String origContent;

    private List<String> origKeywordList;
}
