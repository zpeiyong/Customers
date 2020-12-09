package com.dataint.monitor.model.form;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class StoreDataForm {

    @NotNull(message = "文章唯一性标识不能为空")
    private String articleKey;  // 文章唯一性标识(中台通过此值做数据幂等), 应取mongodb的_id

    @NotNull(message = "文章信息不能为空")
    @Valid
    private ArticleForm articleForm;  // 文章form类

    private ArticleOriginForm articleOriginForm;  // 源语言文章form类

    private ArticleExtForm articleExtForm;  // 文章扩展form类

    @NotNull(message = "网站信息不能为空")
    @Valid
    private SiteForm siteForm;  // 爬取网站form类

    private List<ArticleAttachForm> articleAttachFormList;  // 附件form类
}
