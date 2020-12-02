package com.dataint.monitor.adapt.impl;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.adapt.IApplicationAdapt;
import com.dataint.monitor.model.form.ApplyForm;
import com.dataint.monitor.model.form.TopicForm;
import com.dataint.monitor.model.form.UpdateTopicForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ApplicationAdaptImpl implements IApplicationAdapt {

    @Value("${service.topic.baseUrl}")
    private String baseUrl;

    @Override
    public Object applyAddTopic(TopicForm topicForm) {
        String url ="http://" +  baseUrl + "/application/applyAddTopic";
        String jsonString = JSONObject.toJSONString(topicForm);
        return GetPostUtil.sendPost(url, jsonString);
    }

    @Override
    public Object applyUpdateTopic(UpdateTopicForm updateTopicForm) {
        String url ="http://" +  baseUrl + "/application/applyUpdateTopic/" + updateTopicForm.getId();
        String jsonString = JSONObject.toJSONString(updateTopicForm);
        return GetPostUtil.sendPost(url, jsonString);
    }

    @Override
    public Object getAllApply(Integer current, Integer pageSize, String keyword) {
        HashMap<String, String> map = new HashMap<>();
        map.put("current", current.toString());
        map.put("pageSize", pageSize.toString());
        if (keyword != null)
            map.put("keyword", keyword);
        String url ="http://" +  baseUrl + "/application/getAllApply";
        return GetPostUtil.sendGet(url, map);
    }

    @Override
    public Object getProcessedApply(Integer current, Integer pageSize, String keyword) {
        HashMap<String, String> map = new HashMap<>();
        map.put("current", current.toString());
        map.put("pageSize", pageSize.toString());
        map.put("keyword", keyword);
        String url ="http://" +  baseUrl + "/application/getProcessedApply";
        return GetPostUtil.sendGet(url, map);
    }

    @Override
    public Object getApplyInfo(Long id, Long topicId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("topicId", topicId.toString());
        String url ="http://" +  baseUrl + "/application/getApplyInfo/" + id.toString();
        return GetPostUtil.sendGet(url, map);
    }

    @Override
    public Object saveApply(ApplyForm applyForm) {
        String url ="http://" +  baseUrl + "/application/saveApply";
        String jsonString = JSONObject.toJSONString(applyForm);
        return GetPostUtil.sendPost(url, jsonString);
    }

    @Override
    public Object refuseApply(Integer id, String feedback) {
        String url ="http://" +  baseUrl + "/application/refuseApply";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("feedback", feedback);
        String jsonString = JSONObject.toJSONString(jsonObject);
        return GetPostUtil.sendPost(url, jsonString);
    }

    @Override
    public Object applyDelTopic(Integer id) {
        String url ="http://" +  baseUrl + "/application/applyDelTopic/" + id.toString();
        return GetPostUtil.sendDelete(url);
    }
}
