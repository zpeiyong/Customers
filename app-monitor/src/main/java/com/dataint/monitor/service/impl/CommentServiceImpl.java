package com.dataint.monitor.service.impl;

import com.dataint.cloud.common.exception.DataNotExistException;
import com.dataint.monitor.dao.IComment;
import com.dataint.monitor.dao.ICommentDao;
import com.dataint.monitor.dao.entity.Comment;
import com.dataint.monitor.model.form.CommentForm;
import com.dataint.monitor.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    ICommentDao commentDao;

    @Override
    public Page<IComment> queryComment(Long articleId, Long userId, int current, int pageSize) {
        Page<IComment> allByUserId = commentDao.getInfosById(articleId,userId,PageRequest.of(current - 1, pageSize));
        return allByUserId;
    }

    @Override
    public Comment saveComment(CommentForm commentForm) {
        Long id = commentForm.getId();
        if (id == null) {
            Comment comment = commentForm.toPo(Comment.class);
            Comment save = commentDao.save(comment);
            return save;
        } else {
            Optional<Comment> commentById = commentDao.findById(id);
            if (!commentById.isPresent()) {
            }
            Comment commentInfos = commentById.get();
            if (commentInfos.getContent() != commentForm.getContent()) {
                commentInfos.setContent(commentForm.getContent());
            }
            if (commentInfos.getArticleId() != commentForm.getArticleId()) {
                commentInfos.setArticleId(commentForm.getArticleId());
            }
            Comment save = commentDao.save(commentInfos);
            return save;
        }
    }

    @Transactional
    @Override
    public Boolean delCommentById(Long id) {
        Optional<Comment> comment = commentDao.findById(id);
        if (!comment.isPresent()) {
            throw new DataNotExistException();
        }
        commentDao.deleteById(id);
        return true;
    }
}
