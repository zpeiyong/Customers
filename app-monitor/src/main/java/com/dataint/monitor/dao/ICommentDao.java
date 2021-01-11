package com.dataint.monitor.dao;

import com.dataint.monitor.dao.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentDao extends JpaRepository<Comment, Long> {

    List<Comment> findAllByArticleId(Integer articleId);

    Integer countByArticleId(Integer articleId);
}
