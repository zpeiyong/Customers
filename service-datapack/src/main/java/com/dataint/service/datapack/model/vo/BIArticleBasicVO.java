package com.dataint.service.datapack.model.vo;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.service.datapack.db.entity.Article;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@NoArgsConstructor
public class BIArticleBasicVO extends BaseVO {

    public BIArticleBasicVO(Article article) {
        BeanUtils.copyProperties(article, this);

        if (article.getSite() != null && !StringUtils.isEmpty(article.getSite().getNameCn())) {
            this.siteName = article.getSite().getNameCn();
        }
    }

    private String title;  // 标题

    private String summary;  // 摘要

    private String siteName;  // 网站name

    private Date gmtRelease;  //

    private Date gmtCrawl;  //

    private String articleUrl; // 文章链接

    private String articleType;  //舆情类型： pubinfo|statistic

}
