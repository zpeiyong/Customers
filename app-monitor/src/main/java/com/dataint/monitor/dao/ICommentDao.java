package com.dataint.monitor.dao;

import com.dataint.monitor.dao.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentDao extends JpaRepository<Comment, Long> {

    Integer countByArticleId(Long articleId);
}
