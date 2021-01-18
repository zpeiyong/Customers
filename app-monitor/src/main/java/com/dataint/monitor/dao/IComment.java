package com.dataint.monitor.dao;

public interface IComment {
    String getContent();
    Long getArticleId();
    Long getUserId();
    String getUserName();
    String getCreateTime();
}
