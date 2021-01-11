package com.dataint.monitor.adapt.impl;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.adapt.IFocusDiseaseAdapt;
import com.dataint.monitor.model.FocusDisease;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class FocusDiseaseAdaptImpl implements IFocusDiseaseAdapt {
    @Value("${service.datapack.baseUrl}")
    private String baseUrl;
    @Override
    public Object listFocusDisease(Long id, String showType) {
        HashMap<String, String> map = new HashMap<>();
        if (id!=null) {
            map.put("id", id.toString());
        }
        if (showType!=null) {
            map.put("showType", showType);
        }
        String url ="http://" +  baseUrl + "/focusDisease/focusDiseaseList";
        return GetPostUtil.sendGet(url, map);

    }

    @Override
    public Object addFocusDisease(FocusDisease focusDisease) {
        String url ="http://" +  baseUrl + "/focusDisease/addFocusDisease";
        String jsonString = JSONObject.toJSONString(focusDisease);
        return GetPostUtil.sendPost(url, jsonString);
    }

    @Override
    public Object listFocusDiseaseDefault() {
        String url ="http://" +  baseUrl + "/focusDisease/defaultFocusDisease";
        return GetPostUtil.sendGet(url);
    }
}
