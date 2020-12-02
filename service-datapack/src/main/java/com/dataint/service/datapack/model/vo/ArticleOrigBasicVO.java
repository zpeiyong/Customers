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
public class ArticleOrigBasicVO extends BaseVO {

    public ArticleOrigBasicVO(ArticleOrigin origin) {
        BeanUtils.copyProperties(origin, this);

        if (!StringUtils.isEmpty(origin.getOrigKeywords()))
            this.origKeywordList = Arrays.asList(origin.getOrigKeywords().split(Constants.SPLITTER));
    }

    private String origTitle;

    private String origSummary;

    private String origContent;

    private List<String> origKeywordList;

}
