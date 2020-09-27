package com.dataint.topic.db.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "dynamic_data")
@Accessors(chain = true)
public class DynamicData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "article_id", nullable = false)
    private Integer articleId;

    @Column(name = "fans_cnt")
    private Integer fansCnt;

    @Column(name = "forward_cnt")
    private Integer forwardCnt;

    @Column(name = "comment_cnt")
    private Integer commentCnt;

    @Column(name = "like_cnt")
    private Integer likeCnt;

    @Column(name = "gmt_crawl")
    private Date gmtCrawl;
}
