package com.dataint.diseasepo.dao.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "article_audit")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleAudit extends BasePO {
    /**
     * 已审核表
     */

    @Column(name = "article_id")
    private Integer articleId;
}
