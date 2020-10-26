package com.dataint.topic.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 动态数据表
 */

@Entity
@Table(name = "dynamic_data")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DynamicData implements Serializable {

    private static final long serialVersionUID = -8848529729733462172L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;  // 动态数据表-主键id

    @Column(name = "article_id", nullable = false)
    private Long articleId;  // 文章id

    @Column(name = "fans_cnt")
    private Integer fansCnt;  // 粉丝数

    @Column(name = "forward_cnt")
    private Integer forwardCnt;  // 转发数

    @Column(name = "comment_cnt")
    private Integer commentCnt;  // 评论数

    @Column(name = "like_cnt")
    private Integer likeCnt;  // 点赞数

    @Column(name = "gmt_crawl")
    private Date gmtCrawl;  // 爬取时间
}
