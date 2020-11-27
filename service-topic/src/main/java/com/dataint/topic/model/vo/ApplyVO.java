package com.dataint.topic.model.vo;

import com.dataint.cloud.common.model.BaseVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApplyVO extends BaseVO {

    private String name;

    private List<String> keywordNames;

    private Boolean ifDeleted;

    private List<ApplicationVO> applicationList;
}
