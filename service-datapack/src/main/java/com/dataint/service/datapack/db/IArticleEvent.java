package com.dataint.service.datapack.db;

public interface IArticleEvent {

    String getTitle();

    String getSummary();

    String getContent();

    String getArticleUrl();

    String getKeywords();

    Long getId();

    String getNameCn();

    String getReleaseTime();
}
