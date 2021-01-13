package com.dataint.monitor.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description  已生成报告的舆情信息记录表
 */

@Entity
@Table( name ="article_report" )
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleReport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "articleId", nullable = false)
    private Long articleId;

    @Column(name = "title")
    private String title;

    @Column(name = "summary")
    private String summary;

    @Column(name = "article_url", length = 1024)
    private String articleUrl;

    @Column(name = "report_title")
    private String reportTitle;

    @Column(name = "report_level_id")
    private Long reportLevelId;
}
