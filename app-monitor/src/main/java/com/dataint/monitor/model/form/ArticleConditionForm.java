package com.dataint.monitor.model.form;

import com.dataint.cloud.common.model.param.PageParam;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
public class ArticleConditionForm extends PageParam implements Serializable {

    private Integer keywordId;

    private ArrayList<Integer> siteIdList;

    private ArrayList<Integer> mediaTypeIdList;

    private Integer topicId;

    private String sortOrder = "desc";  // "desc", "asc"

}
