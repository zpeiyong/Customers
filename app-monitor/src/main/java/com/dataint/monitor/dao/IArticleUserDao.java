package com.dataint.monitor.dao;

import com.dataint.monitor.dao.entity.ArticleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IArticleUserDao extends JpaRepository<ArticleUser, Integer> {

    ArticleUser findByUserIdAndArticleId(int userId, int articleId);
}
