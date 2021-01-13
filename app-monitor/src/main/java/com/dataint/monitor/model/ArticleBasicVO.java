package com.dataint.monitor.model;

import com.dataint.cloud.common.model.BaseVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ArticleBasicVO extends BaseVO {

    private String title;  // 标题

    private String summary;  // 摘要

    private String content;  // 内容

    private List<String> keywordList;  // 舆情关键词列表

    private String siteName;  // 网站name

    private Date gmtRelease;  //

    private String articleUrl; // 文章链接

    private ArticleOrigBasicVO articleOriginVO;  //

    private String articleType;  //舆情类型： pubinfo|statistic

    /*  */
    private Integer similarArticleCnt;  // 相似文章数量

    private Boolean ifSimilar;  // 是否已关联到相似文章

    /* app-monitor:
        - 当前用户是否关注
        - 评审数量
        - 是否已加入日报
     */
    private Boolean ifLike = false; // 当前用户是否关注, 默认没有关注

}
