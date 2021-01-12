package com.dataint.service.datapack.db.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "article")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BasePO {

    @Column(name = "article_key", nullable = false)
    private String articleKey;  // 文章唯一性标识(中台通过此值做数据幂等), 应取mongodb的_id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id")
    private Site site;  // 网站

//    @Column(name = "site_id")
//    private Long siteId;  // 网站id

    /*
    [国家|传染病]通过存在对应关系
     */
//    @Column(name = "country_code")
//    private String countryCode;  // 国家编码(多个国家用|区分)

//    @Column(name = "event_type")
//    private String eventType;  // 文章类型(文章对应传染病类型) - ["", "", "", ""]


    @Column(name = "title", nullable = false)
    private String title;  // 标题

//    @Column(name = "sub_title")
//    private String subTitle;  // 副标题

    @Column(name = "author")
    private String author;  // 作者

    @Column(name = "editor")
    private String editor;  // 编辑

    @Column(name = "summary", length = 2000)
    private String summary;  // 摘要

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content", columnDefinition = "MediumText")
    private String content;  // 内容

    @Column(name = "keywords", length = 500)
    private String keywords;   // 关键字('|'分隔)

    @Column(name = "article_url", length = 1024)
    private String articleUrl; // 文章链接

    @Column(name = "origin_url", length = 1024)
    private String originUrl;  //

    @Column(name = "gmt_release")
    private Date gmtRelease; // 发表时间

    @Column(name = "gmt_crawl")
    private Date gmtCrawl;  // 爬取时间

    @Column(name = "input_md5")
    private String inputMd5;  // 传入数据进行md5去重

    @Column(name = "if_deleted", columnDefinition = "tinyint(1) default 0")
    private Boolean ifDeleted = false;  // 是否已删除数据(false:未删除; true:已删除)

    @Column(name = "article_type")
    private String articleType;  // 舆情文章内容类型(statistic:报告 ; pubinfo:新闻)

    @Column(name = "media_type_id")
    private Long mediaTypeId;  // 来源媒体类型id

    @Column(name = "if_similar")
    private Boolean ifSimilar = false;  // 是否已关联到相似文章(true:已走相似度算法 ; false: 未走相似度算法)

    @Column(name = "similar_article_id")
    private Long similarArticleId = -1L;  // 归类相似文章id(当前文章为首篇则id=0)

//    @Column(name = "outbreak_judgment", columnDefinition = "char(1) default 'N'")
//    private String outbreakJudgment = "N"; //是否计入事件统计(N:不计入; Y:计入)

    /**
     * 舆情对应相关表
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "article_ext_id")
    private ArticleExt articleExt;  // 舆情拓展实体

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "article_origin_id")
    private ArticleOrigin articleOrigin;  // 舆情原语言实体

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private List<ArticleAttach> attachList;  // 舆情附件实体列表

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private List<ArticleDisease> diseaseList;  //  舆情对应疫情信息列表( [国家-传染病]存在对应关系 )
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "outbreak_level_id")
//    private OutbreakLevel outbreakLevel;  // 舆情等级实体
}
