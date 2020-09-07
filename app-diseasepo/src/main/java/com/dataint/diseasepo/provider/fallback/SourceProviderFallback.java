package com.dataint.diseasepo.provider.fallback;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.cloud.common.utils.JSONUtil;
import com.dataint.diseasepo.model.form.SourceForm;
import com.dataint.diseasepo.model.form.SourceUpdateForm;
import com.dataint.diseasepo.provider.SourceProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SourceProviderFallback implements SourceProvider {
    @Value("${service.datapack.baseUrl}")
    private String baseUrl;

    @Override
    public ResultVO add(SourceForm sourceForm) {
        JSONObject jsonObject = GetPostUtil.sendPost(baseUrl + "/source/add", JSONUtil.toJSon(sourceForm), 8000);
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO updateSource(SourceUpdateForm sourceUpdateForm) {
        JSONObject jsonObject = GetPostUtil.sendPut(baseUrl + "/source/update", JSONUtil.toJSon(sourceUpdateForm), 8000);
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO delSource(Integer sourceId) {
        JSONObject jsonObject = GetPostUtil.sendDelete(baseUrl + "/source/delete/{sourceId}");
        return ResultVO.success(jsonObject);
    }
}
