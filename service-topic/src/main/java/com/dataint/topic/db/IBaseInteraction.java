package com.dataint.topic.db;

public interface IBaseInteraction extends IBase{

    Long getForwardCnt();

    Long getCommentCnt();

    Long getLikeCnt();

    Long getTotalCnt();
}
