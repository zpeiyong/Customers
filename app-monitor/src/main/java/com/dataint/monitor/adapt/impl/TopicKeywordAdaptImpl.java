package com.dataint.monitor.adapt.impl;

import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.adapt.ITopicKeywordAdapt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TopicKeywordAdaptImpl implements ITopicKeywordAdapt {

    @Value("${service.topic.baseUrl}")
    private String baseUrl;


    @Override
    public Object getKeywordListByTopicId(Integer topicId) {
        String url ="http://" +  baseUrl + "/topicKeyword/getKeywordListByTopicId/" + topicId.toString();
        return GetPostUtil.sendGet(url);
    }
}
