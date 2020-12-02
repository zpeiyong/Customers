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

@Data
@NoArgsConstructor
public class ArticleVO extends BaseVO {

    public ArticleVO(Article article) {
        BeanUtils.copyProperties(article, this);

        if (!StringUtils.isEmpty(article.getKeywords()))
            this.keywordList = Arrays.asList(article.getKeywords().split(Constants.SPLITTER));
        if (article.getSite() != null)
            this.siteVO = new SiteVO(article.getSite());
        if (article.getArticleExt() != null)
            this.articleExtVO = new ArticleExtVO(article.getArticleExt());
        if (article.getArticleOrigin() != null)
            this.articleOriginVO = new ArticleOriginVO(article.getArticleOrigin());
//        if (article.getDiseaseList() != null) {
//            List<ArticleDiseaseVO> diseaseVOList = new ArrayList<>();
//            for (ArticleDisease articleDisease : article.getDiseaseList()) {
//                ArticleDiseaseVO diseaseVO = new ArticleDiseaseVO(articleDisease);
//                // 若舆情对应疫情没有国家信息&&舆情下有国家信息, 则赋给舆情的所有疫情
//                if (!StringUtils.isEmpty(article.getCountryCode()) && CollectionUtils.isEmpty(diseaseVO.getCountryCodeList())) {
//                    List<String> countryCodeList= Arrays.stream(article.getCountryCode().split(Constants.SPLITTER))
//                            .collect(Collectors.toList());
//                    diseaseVO.setCountryCodeList(countryCodeList);
//                }
//                diseaseVOList.add(diseaseVO);
//            }
//            this.diseaseVOList = diseaseVOList;
//        }
    }

    private String articleKey;  // 文章唯一性标识(中台通过此值做数据幂等), 应取mongodb的_id

    private SiteVO siteVO;  // 网站VO

//    private String countryCode;  // 国家编码(舆情vo时不显示国家信息, 国家信息赋给疫情)

    private String title;  // 标题

//    private String subTitle;  // 副标题

    private String author;  // 作者

//    private String editor;  // 编辑

    private String summary;  // 摘要

    private String content;  // 内容

    private List<String> keywordList;   // 关键词列表

    private String articleUrl; // 文章链接

//    private String originUrl;  //

    private Date gmtRelease; // 发表时间

    private Date gmtCrawl;  // 爬取时间

//    private Date createdTime;
//
//    private Date createdBy;
//
//    private Date updatedTime;
//
//    private Date updatedBy;

    private ArticleExtVO articleExtVO;

    private ArticleOriginVO articleOriginVO;

    private List<ArticleAttach> attachList;  // 舆情附件实体列表

    private List<ArticleDiseaseVO> diseaseVOList;  //  舆情对应疫情信息列表

//    private OutbreakLevel outbreakLevel;  // 舆情等级实体

    private String articleType;  // 疫情类型：statistic|pubinfo
}
