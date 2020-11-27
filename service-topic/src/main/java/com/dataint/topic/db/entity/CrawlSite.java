package com.dataint.topic.db.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * 专题爬取网站表
 */

@Entity
@Table(name = "crawl_site")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrawlSite extends BasePO {

    @Column(name = "name_cn")
    private String nameCn;  // 中文名称

    @Column(name = "name_en")
    private String nameEn;  // 英文名称

    @Column(name = "url", length = 1024)
    private String url;  // 爬取链接

    @Column(name = "project_name", length = 64)
    private String projectName;  // 爬虫脚本默认名称

    @Column(name = "script", columnDefinition = "text")
    private String script;  // 爬虫脚本

    @Column(name = "rate_burst", length = 16)
    private String rateBurst;  // 爬虫速率

    @Column(name = "language")
    private String language;  // 语言

    @Column(name = "enable")
    private Boolean enable;  // 是否可用(1:可用; 2:不可用)

}
