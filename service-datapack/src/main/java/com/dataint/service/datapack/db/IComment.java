package com.dataint.service.datapack.db;

public interface IComment
{
//    c.content,c.articleId,c.createdBy,c.userId

  String getContent();
  Long getArticleId();
  int getUserId();
  String getUserName();
}
