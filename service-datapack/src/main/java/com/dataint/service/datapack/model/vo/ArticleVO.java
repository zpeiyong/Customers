package com.dataint.service.datapack.model.vo;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.cloud.common.model.Constants;
import com.dataint.service.datapack.db.entity.Article;
import com.dataint.service.datapack.db.entity.ArticleAttach;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ArticleVO extends BaseVO {

    public ArticleVO(Article article) {
        BeanUtils.copyProperties(article, this);

        if (!StringUtils.isEmpty(article.getKeywords())) {
            this.keywordList = Arrays.asList(article.getKeywords().split(Constants.SPLITTER));
        }
        if (article.getSite() != null) {
            this.siteVO = new SiteVO(article.getSite());
        }
        if (article.getArticleExt() != null) {
            this.articleExtVO = new ArticleExtVO(article.getArticleExt());
        }
        if (article.getArticleOrigin() != null) {
            this.articleOriginVO = new ArticleOriginVO(article.getArticleOrigin());
        }
        if (article.getDiseaseList() != null) {
            List<ArticleDiseaseVO> diseaseVOList = article.getDiseaseList()
                    .stream()
                    .map(ArticleDiseaseVO::new)
                    .collect(Collectors.toList());
            this.diseaseVOList = diseaseVOList;
        }
    }

    private String articleKey;  // 文章唯一性标识(中台通过此值做数据幂等), 应取mongodb的_id

    private SiteVO siteVO;  // 网站VO

    private String title;  // 标题

    private String author;  // 作者

    private String summary;  // 摘要

    private String content;  // 内容

    private List<String> keywordList;   // 关键词列表

    private String articleUrl; // 文章链接

    private Date gmtRelease; // 发表时间

    private Date gmtCrawl;  // 爬取时间

    private Date createdTime;

    private Date createdBy;

    private Date updatedTime;

    private Date updatedBy;

    private ArticleExtVO articleExtVO;

    private ArticleOriginVO articleOriginVO;

    private List<ArticleAttach> attachList;  // 舆情附件实体列表

    private List<ArticleDiseaseVO> diseaseVOList;  //  舆情对应疫情信息列表

    private String articleType;  // 疫情类型：statistic|pubinfo
}
