package com.dataint.monitor.model.form;

import com.dataint.cloud.common.model.form.BaseForm;
import com.dataint.monitor.model.enums.AttachTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ArticleAttachForm extends BaseForm {

//    private Integer id;  // 附件id, 请求存入时为空
//
//    private Integer articleId;  // 文章id, 请求存入时为空

    @NotNull
    private AttachTypeEnum attachType;

    @NotNull
    private String attachUrl;
}
