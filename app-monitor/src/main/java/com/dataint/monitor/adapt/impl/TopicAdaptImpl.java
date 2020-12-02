package com.dataint.monitor.adapt.impl;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.adapt.ITopicAdapt;
import com.dataint.monitor.model.form.TopicForm;
import com.dataint.monitor.model.form.UpdateTopicForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class TopicAdaptImpl implements ITopicAdapt {

    @Value("${service.topic.baseUrl}")
    private String baseUrl;

    @Override
    public Object addTopic(TopicForm topicForm) {
        String url ="http://" +  baseUrl + "/topic/addTopic";
        String jsonString = JSONObject.toJSONString(topicForm);
        return GetPostUtil.sendPost(url, jsonString);
    }

    @Override
    public Object delTopic(Long id) {
        String url ="http://" +  baseUrl + "/topic/delTopic/" + id.toString();
        return GetPostUtil.sendDelete(url);
    }

    @Override
    public Object recoveryTopic(Long id) {
        String url ="http://" +  baseUrl + "/topic/recoveryTopic/" + id.toString();
        return GetPostUtil.sendDelete(url);
    }

    @Override
    public Object updateTopic(UpdateTopicForm updateTopicForm) {
        String url ="http://" +  baseUrl + "/topic/updateTopic/" + updateTopicForm.getId();
        String jsonString = JSONObject.toJSONString(updateTopicForm);
        return GetPostUtil.sendPut(url, jsonString, 3000);
    }

    @Override
    public Object getAllActiveTopic() {
        String url ="http://" +  baseUrl + "/topic/getAllActiveTopic";
        return GetPostUtil.sendGet(url);
    }

    @Override
    public Object getAllDeleteTopic() {
        String url ="http://" +  baseUrl + "/topic/getAllDelTopic";
        return GetPostUtil.sendGet(url);
    }

    @Override
    public Object getAllTopic(Integer current, Integer pageSize, String keyword) {
        HashMap<String, String> map = new HashMap<>();
        map.put("current", current.toString());
        map.put("pageSize", pageSize.toString());
        map.put("keyword", keyword);
        String url ="http://" +  baseUrl + "/topic/getAllTopic";
        return GetPostUtil.sendGet(url, map);
    }
}
