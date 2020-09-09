package com.dataint.topic.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ArticleConditionReq extends PageParam implements Serializable {

    private int keywordId;

    private int siteId;

    private int mediaTypeId;

    private String sortOrder = "desc";  // "desc", "asc"

}
