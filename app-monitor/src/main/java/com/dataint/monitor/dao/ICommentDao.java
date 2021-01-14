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
      @Query(nativeQuery = true, value = "SELECT u.username as userName,com.article_id as articleId,com.content as content,com.user_id as userId " +
              "from comment com left JOIN `user` u " +
              "on com.user_id= u.id   where  com.article_id =?1 and  u.id =?2 ",

              countQuery="SELECT u.username as userName,com.article_id as articleId,com.content as content,com.user_id as userId " +
                      "from comment com left JOIN `user` u " +
                      "on com.user_id= u.id   where  com.article_id =?1 and  u.id =?2 "
      )

      Page<IComment> getInfosById(Long articleId, Long userId, Pageable pageable);
}