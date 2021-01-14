package com.dataint.monitor.dao;

import com.dataint.monitor.dao.entity.Comment;
import com.dataint.monitor.model.CommentVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentDao extends JpaRepository<Comment, Long> {

    Integer countByArticleId(Long articleId);


//    @Query(value = "SELECT u.username,c " +
//            "from  User u,comment c   where c.article_id = ?1 and u.id = ?2")
    @Query(value = "select  new com.dataint.monitor.model.CommentVo from `comment` c,`user` u " +
            "where c.article_id = ?1 and u.id = ?2 ", nativeQuery = true)
    Page<CommentVo> getInfosById(Long articleId, Long userId, Pageable pageable);
}