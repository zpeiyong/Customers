package com.dataint.service.datapack.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel
@Data
public class ArticleUpdateForm {

    @ApiModelProperty(value = "舆情表-主键Id")
    @NotNull
    private Long articleId;

    @ApiModelProperty(value = "舆情摘要")
    private String summary;

    @ApiModelProperty(value = "舆情对应疫情列表")
    private List<ArticleDiseaseForm> diseaseFormList;
}
