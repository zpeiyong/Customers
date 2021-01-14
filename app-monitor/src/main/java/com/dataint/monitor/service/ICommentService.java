package com.dataint.monitor.service;

import com.dataint.monitor.dao.IComment;
import com.dataint.monitor.dao.entity.Comment;
import com.dataint.monitor.model.form.CommentForm;
import org.springframework.data.domain.Page;

public interface ICommentService {

    //查询评论信息
    Page<IComment> queryComment(Long articleId, Long userId, int current, int pageSize);

    Comment saveComment(CommentForm commentForm);

    Boolean  delCommentById(Long id);

}
