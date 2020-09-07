package com.dataint.service.datapack.model.form;

import com.dataint.cloud.common.model.form.BaseForm;
import com.dataint.service.datapack.dao.entity.Article;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class ArticleForm extends BaseForm<Article> {
//    private Integer id;  // 文章id, 请求存入时为空

    @NotNull
    private String articleKey; // 文章唯一性标识(中台通过此值做数据幂等), 应取mongodb的_id

    @NotNull
    private String articleUrl; // 文章链接

    @NotNull
    private String title;  // 标题

    private String subTitle;  // 副标题

    private String author;  // 作者

    private String editor;  // 编辑

    private String summary;  // 摘要

    private String content;  // 内容

    private List<String> keywordList;   // 关键字

    @NotNull
    private Date gmtRelease; // 发表时间

    private Date gmtCrawl;  // 爬取时间

    private List<String> eventTypeList; // 文章类型(文章对应传染病类型)

    private List<String> countryNameList;  // 国家名称(可能中文可能英文或其他语言)

}
