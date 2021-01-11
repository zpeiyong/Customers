package com.dataint.topic.model.vo;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.topic.db.entity.TopicArticle;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
public class TopicArticleVO extends BaseVO {

    public TopicArticleVO(TopicArticle topicArticle) {
        BeanUtils.copyProperties(topicArticle, this);
        if (topicArticle.getSite() != null) {
            this.setSiteNameCn(topicArticle.getSite().getNameCn());
            this.setSiteUrl(topicArticle.getSite().getUrl());
        }

    }

    private String articleKey;  // 原始数据在mongodb的ObjectId值

    private String articleUrl;  // 文章链接

    private Long siteId;  // 爬取网站id

    private String author;  // 来源(机构/个人等)

    private String title;  // 标题

    private String subTitle;  // 副标题

    private String summary;  // 摘要

    private Long keywordId;  // 关键词id

    private String content;  // 文章正文

    private String originUrl;  // 文章来源源地址

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date gmtCrawl;  // 爬取时间

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date gmtRelease;  // 文章发布时间

    private Long mediaTypeId;  // 媒体类型id

    private String createdBy;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    private String updatedBy;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

    // site
    private String siteUrl;

    private String siteNameCn;  // 中文名称

}
