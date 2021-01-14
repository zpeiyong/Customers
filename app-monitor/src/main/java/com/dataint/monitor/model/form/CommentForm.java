package com.dataint.monitor.model.form;

import com.dataint.cloud.common.model.form.BaseForm;
import com.dataint.monitor.dao.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm extends BaseForm<Comment> {

    private Long id;

    private Long articleId;  // 文章id

    private String content;  // 评论内容

    private Long userId;  // 评论用户id
}
