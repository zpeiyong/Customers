package com.dataint.monitor.dao;

import com.dataint.monitor.dao.entity.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IArticleLikeDao extends JpaRepository<ArticleLike, Long> {

   List<Long> findArticleIdByUserId(Long userId);
}
