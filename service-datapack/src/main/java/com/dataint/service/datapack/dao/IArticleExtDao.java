package com.dataint.service.datapack.dao;

import com.dataint.service.datapack.dao.entity.ArticleExt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IArticleExtDao extends JpaRepository<ArticleExt, Integer> {
}
