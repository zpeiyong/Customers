package com.dataint.monitor.provider.fallback;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.ResultVO;

import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.cloud.common.utils.JSONUtil;
import com.dataint.monitor.model.form.DiseaseForm;
import com.dataint.monitor.model.form.DiseaseUpdateForm;
import com.dataint.monitor.provider.DiseaseProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DiseaseProviderFallback implements DiseaseProvider {
    @Value("${service.datapack.baseUrl}")
    private String baseUrl;

    @Override
    public ResultVO addDisease(DiseaseForm diseaseForm) {
        JSONObject jsonObject = GetPostUtil.sendPost(baseUrl + "/disease", JSONUtil.toJSon(diseaseForm), 8000);
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO updateDiseaseStatus(Integer diseaseId, Integer status) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("diseaseId",diseaseId);
        jsonObject.put("status",status);
        JSONObject jsonObjects = GetPostUtil.sendPut(baseUrl + "/disease/status/{diseaseId}", JSONUtil.toJSon(jsonObject), 8000);
        return ResultVO.success(jsonObjects);
    }

    @Override
    public ResultVO updateDisease(DiseaseUpdateForm diseaseUpdateForm) {
        JSONObject jsonObject = GetPostUtil.sendPut(baseUrl + "/disease", JSONUtil.toJSon(diseaseUpdateForm), 8000);
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO delDisease(Integer diseaseId) {
        JSONObject jsonObject = GetPostUtil.sendDelete(baseUrl + "/disease/{diseaseId}");
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO getDisease(Integer diseaseId) {
        JSONObject jsonObject = GetPostUtil.sendGet(baseUrl + "/disease/{diseaseId}");
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO getDiseases(String keyword, Integer current, Integer pageSize) {
        JSONObject jsonObject = GetPostUtil.sendGet(baseUrl + "/disease/all");
        return ResultVO.success(jsonObject);
    }
}
