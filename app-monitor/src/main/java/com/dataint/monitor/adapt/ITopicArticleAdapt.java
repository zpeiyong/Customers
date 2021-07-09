package com.dataint.monitor.adapt;

import com.dataint.monitor.model.form.ArticleConditionForm;

public interface ITopicArticleAdapt {
    Object queryArticlesByCondition(ArticleConditionForm acReq);

    Object getArticleById(Long id);

    Object getPopularFeelingsTj(String gmtDate);
}
