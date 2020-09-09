package com.dataint.monitor.dao.entity;

import com.dataint.cloud.common.model.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "article_user")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUser extends BasePO {

    @Column(name = "article_id")
    private Integer articleId;

    @Column(name = "user_id")
    private Integer userId;

}
