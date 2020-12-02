package com.dataint.monitor.adapt.impl;

import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.adapt.ITopicArticleAdapt;
import com.dataint.monitor.model.form.ArticleConditionForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TopicArticleAdaptImpl implements ITopicArticleAdapt {

    @Value("${service.topic.baseUrl}")
    private String baseUrl;

    @Override
    public Object queryArticlesByCondition(ArticleConditionForm acReq) {
        HashMap<String, String> map = new HashMap<>();
        map.put("current", acReq.getCurrent().toString());
        map.put("pageSize", acReq.getPageSize().toString());
        if (acReq.getTopicId() != null)
            map.put("topicId", acReq.getTopicId().toString());
        if (acReq.getKeywordId() != null)
            map.put("keywordId",acReq.getKeywordId().toString());
        if (acReq.getSortOrder() != null)
            map.put("sortOrder", acReq.getSortOrder());
        if (acReq.getSiteIdList() != null && acReq.getSiteIdList().size() > 0)
            map.put("siteIdList", acReq.getSiteIdList().stream().map(Object::toString).collect(Collectors.joining(",")));
        if (acReq.getMediaTypeIdList() != null && acReq.getMediaTypeIdList().size() > 0)
            map.put("mediaTypeIdList",acReq.getMediaTypeIdList().stream().map(Objects::toString).collect(Collectors.joining(",")));
        String url ="http://" +  baseUrl + "/topicArticle/getArticleList";
        return GetPostUtil.sendGet(url, map);
    }

    @Override
    public Object getArticleById(Long id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id.toString());
        String url ="http://" +  baseUrl + "/topicArticle/getArticleById";
        return GetPostUtil.sendGet(url, map);
    }
}
