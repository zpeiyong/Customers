package com.dataint.service.datapack.model.form;

import com.dataint.cloud.common.model.form.BaseForm;
import com.dataint.service.datapack.db.entity.Article;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class ArticleForm extends BaseForm<Article> {

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

//    private List<String> eventTypeList; // 文章类型(文章对应传染病类型)
//
//    private List<String> countryNameList;  // 国家名称(可能中文可能英文或其他语言)

    private String articleType;  // 舆情文章内容类型(statistic:报告 ; pubinfo:新闻)

    private Map<String, String> countryDiseaseRels;  // [国家-传染病]对应关系 ({"巴西": "黄热病", "test": "123"})

}
