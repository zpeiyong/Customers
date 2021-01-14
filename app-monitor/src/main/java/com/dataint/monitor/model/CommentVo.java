package com.dataint.monitor.model;

import com.dataint.cloud.common.model.BaseVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentVo  extends BaseVO {

    private Long articleId;  // 主题库id

    private String content;  // 评论内容

    private Long userId;  // 评论用户id

    private String username;//用户名

}
