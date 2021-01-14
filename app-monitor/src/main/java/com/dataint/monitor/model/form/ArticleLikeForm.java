package com.dataint.monitor.model.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ArticleLikeForm  {

    private Long   id;
    private Long userId;  // 点赞用户id

    private Long articleId;  // 点赞舆情id

    private Date createdTime;  // 点赞时间
}
