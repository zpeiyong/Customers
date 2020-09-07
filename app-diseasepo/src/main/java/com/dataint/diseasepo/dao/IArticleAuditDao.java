package com.dataint.diseasepo.dao;

import com.dataint.diseasepo.dao.entity.ArticleAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IArticleAuditDao extends JpaRepository<ArticleAudit, Integer> {

    ArticleAudit findByArticleId(int articleId);
}
