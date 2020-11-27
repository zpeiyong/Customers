package com.dataint.topic.model.form;


import com.dataint.cloud.common.model.form.BaseForm;
import com.dataint.topic.db.entity.Application;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApplyForm extends BaseForm {

    @ApiModelProperty(value = "专题组id")
    private long id;

    @ApiModelProperty(value = "专题组名称")
    private String name;

    @ApiModelProperty(value = "专题组拥有的关键词列表")
    private List<String> keywordNames;

    @ApiModelProperty(value = "是否删除")
    private Boolean ifDeleted;

    @ApiModelProperty(value = "申请列表")
    private List<Application> applicationList;
}
