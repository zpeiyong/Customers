package com.dataint.topic.service;



public interface IPoKeywordService {

    Object getPoKeywordList();

    Object addPoKeyword(String poKeyword);

    Object updateStatusById(Integer keywordId, String statusType);

    Object deletePoKeywordById(Integer keywordId);
}
