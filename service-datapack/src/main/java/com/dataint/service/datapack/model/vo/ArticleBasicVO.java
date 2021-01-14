package com.dataint.service.datapack.model.vo;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.cloud.common.model.Constants;
import com.dataint.service.datapack.db.entity.Article;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ArticleBasicVO extends BaseVO {

    public ArticleBasicVO(Article article) {
        BeanUtils.copyProperties(article, this);

        if (!StringUtils.isEmpty(article.getKeywords()))
            this.keywordList = Arrays.asList(article.getKeywords().split(Constants.SPLITTER));
        if (article.getSite() != null && !StringUtils.isEmpty(article.getSite().getNameCn()))
            this.siteName = article.getSite().getNameCn();
        if (article.getArticleOrigin() != null) {
            this.articleOriginVO = new ArticleOrigBasicVO(article.getArticleOrigin());
        }
    }

    private String title;  // 标题

    private String summary;  // 摘要

    private String content;  // 内容

    private List<String> keywordList;  // 舆情关键词列表

    private String siteName;  // 网站name

    private Date gmtRelease;  //

    private Date gmtCrawl;  //

    private String articleUrl; // 文章链接

    private ArticleOrigBasicVO articleOriginVO;  //

    private String articleType;  //舆情类型： pubinfo|statistic

    /*  */
    private Integer similarArticleCnt;  // 相似文章数量

    private Boolean ifSimilar;  // 是否已关联到相似文章



}
