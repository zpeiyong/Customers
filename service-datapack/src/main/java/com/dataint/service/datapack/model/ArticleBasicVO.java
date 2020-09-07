package com.dataint.service.datapack.model;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.cloud.common.model.Constants;
import com.dataint.service.datapack.dao.entity.Article;
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
        if (article.getOutbreakLevel() != null) {
            this.levelVO = new OutbreakLevelVO(article.getOutbreakLevel());
        }
    }

    private String title;

    private String summary;  //

    private String content;

    private List<String> keywordList;  // 舆情关键词列表

    private String siteName;  // 网站name

    private Date gmtRelease;  //

    private String articleUrl; // 文章链接

    private ArticleOrigBasicVO articleOriginVO;  //

    private OutbreakLevelVO levelVO;  // 舆情等级实体

    private String articleType;  //舆情类型： disease|article
}
