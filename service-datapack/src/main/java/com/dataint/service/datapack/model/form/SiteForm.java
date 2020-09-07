package com.dataint.service.datapack.model.form;

import com.dataint.cloud.common.model.form.BaseForm;
import com.dataint.service.datapack.dao.entity.Site;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SiteForm extends BaseForm<Site> {

    @NotNull(message = "网站url不能为空")
    private String url;

    @NotNull(message = "网站名不能为空")
    private String nameCn;  // 网站名称-中文

    private String nameEn;  // 网站名称-英文

    private String nameOrigin;  // 网站名称-源语言

    @NotNull(message = "网站语言不能为空")
    private String language;

}
