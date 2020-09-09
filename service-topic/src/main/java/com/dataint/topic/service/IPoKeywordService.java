package com.dataint.topic.service;

import com.dataint.topic.common.exception.ThinventBaseException;

public interface IPoKeywordService {

    Object getPoKeywordList() throws ThinventBaseException;

    Object addPoKeyword(String poKeyword) throws ThinventBaseException;

    Object updateStatusById(Integer keywordId, String statusType) throws ThinventBaseException;

    Object deletePoKeywordById(Integer keywordId) throws ThinventBaseException;
}
