package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.entity.ArticleAttach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IArticleAttachDao extends JpaRepository<ArticleAttach, Integer> {
}
