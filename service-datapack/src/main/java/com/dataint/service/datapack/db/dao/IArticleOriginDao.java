package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.entity.ArticleOrigin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IArticleOriginDao extends JpaRepository<ArticleOrigin, Long> {
}
