package com.dataint.diseasepo.provider.fallback;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.cloud.common.utils.JSONUtil;
import com.dataint.diseasepo.model.form.EventForm;
import com.dataint.diseasepo.model.form.EventUpdateForm;
import com.dataint.diseasepo.model.param.EventQueryParam;
import com.dataint.diseasepo.provider.EventProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventProviderFallback implements EventProvider {
    @Value("${service.datapack.baseUrl}")
    private String baseUrl;

    @Override
    public ResultVO add(EventForm eventForm) {
        JSONObject jsonObject = GetPostUtil.sendPost(baseUrl + "/event/add", JSONUtil.toJSon(eventForm));
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO listAll(EventQueryParam eventQueryParam) {
        JSONObject jsonObject = GetPostUtil.sendPost(baseUrl + "/event/all", JSONUtil.toJSon(eventQueryParam), 8000);
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO delete(Integer id) {
        JSONObject jsonObject = GetPostUtil.sendDelete(baseUrl + "/event/{id}");
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO updateEnabled(Integer id, String enabled) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("enable",enabled);
        JSONObject jsonObjects = GetPostUtil.sendPut(baseUrl + "/event/updateEnabled/{id}", JSONUtil.toJSon(jsonObject), 8000);
        return ResultVO.success(jsonObjects);
    }

    @Override
    public ResultVO updateEvent(EventUpdateForm eventUpdateForm) {
        JSONObject jsonObject = GetPostUtil.sendPut(baseUrl + "/event/update", JSONUtil.toJSon(eventUpdateForm), 8000);
        return ResultVO.success(jsonObject);
    }
}
