package com.dataint.service.datapack.dao;

import com.dataint.service.datapack.dao.entity.ArticleAttach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IArticleAttachDao extends JpaRepository<ArticleAttach, Integer> {
}
