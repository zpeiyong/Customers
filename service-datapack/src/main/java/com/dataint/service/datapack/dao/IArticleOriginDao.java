package com.dataint.service.datapack.dao;

import com.dataint.service.datapack.dao.entity.ArticleOrigin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IArticleOriginDao extends JpaRepository<ArticleOrigin, Integer> {
}
