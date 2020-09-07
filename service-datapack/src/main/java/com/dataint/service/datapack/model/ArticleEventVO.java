package com.dataint.service.datapack.model;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.cloud.common.model.Constants;
import com.dataint.service.datapack.dao.entity.Article;
import com.dataint.service.datapack.dao.entity.Event;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.awt.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ArticleEventVO extends BaseVO {

    public ArticleEventVO(Article article) {
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

    private List<Event> eventList; //疫情事件对象

    private String articleType;  //疫情类型：article|disease
}

