package com.dataint.topic.db.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "topic_article")
@Accessors(chain = true)
public class TopicArticle extends BasePO {

    @Column(name = "article_key", nullable = false)
    private String  articleKey;  // 原始数据在mongodb的ObjectId值

    @Column(name = "article_url", length = 1024)
    private String  articleUrl;  // 文章链接

    @Column(name = "site_id", nullable = false)
    private Long siteId;  // 爬取网站id

    @Column(name = "author")
    private String  author;  // 来源(机构/个人等)

    @Column(name = "title")
    private String  title;  // 标题

    @Column(name = "sub_title")
    private String  subTitle;  // 副标题

    @Column(name = "summary", length = 2000)
    private String  summary;  // 摘要

    @Column(name = "keyword_id", nullable = false)
    private Long keywordId;  // 关键词id

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;  // 文章正文

    @Column(name = "origin_url", length = 1024)
    private String  originUrl;  // 文章来源源地址

    @Column(name = "gmt_crawl")
    private Date gmtCrawl;  // 爬取时间

    @Column(name = "gmt_release")
    private Date gmtRelease;  // 文章发布时间

    @Column(name = "media_type_id")
    private Long mediaTypeId;  // 媒体类型id

    @ManyToOne
    @JoinColumn(name = "site_id",insertable = false, updatable = false,foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private CrawlSite site;
}
