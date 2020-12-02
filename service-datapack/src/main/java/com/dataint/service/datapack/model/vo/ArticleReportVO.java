package com.dataint.service.datapack.model.vo;

import com.dataint.cloud.common.model.BaseVO;
import com.dataint.service.datapack.db.entity.Article;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@NoArgsConstructor
public class ArticleReportVO extends BaseVO {

    public ArticleReportVO(Article article) {
        BeanUtils.copyProperties(article, this);
    }

    private String title;

    private String summary;

    private Date gmtRelease;

    private String articleUrl; // 文章链接
}
