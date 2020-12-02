package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.entity.ArticleExt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IArticleExtDao extends JpaRepository<ArticleExt, Integer> {
}
