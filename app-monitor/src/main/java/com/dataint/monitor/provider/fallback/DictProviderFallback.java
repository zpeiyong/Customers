package com.dataint.monitor.provider.fallback;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.provider.DictProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@Slf4j
public class DictProviderFallback implements DictProvider {
    @Value("${service.datapack.baseUrl}")
    private String baseUrl;

    @Override
    public ResultVO queryOutbreakLevels() {
        JSONObject jsonObject = GetPostUtil.sendGet(baseUrl + "/dict/outbreakLevels");
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO queryRegions() {
        JSONObject jsonObject = GetPostUtil.sendGet(baseUrl + "/dict/regions");
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO queryCountries() {
        JSONObject jsonObject = GetPostUtil.sendGet(baseUrl + "/dict/countries");
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO queryDiseases() {
        JSONObject jsonObject = GetPostUtil.sendGet(baseUrl + "/dict/diseases");
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO queryByNameCnFirst(@RequestParam("nameCnFirst") String nameCnFirst) {
        JSONObject jsonObject = GetPostUtil.sendGet(baseUrl + "/dict/disease/queryByNameCnFirst");
        return ResultVO.success(jsonObject);
    }
}
