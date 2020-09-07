package com.dataint.diseasepo.provider.fallback;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.cloud.common.utils.JSONUtil;
import com.dataint.diseasepo.model.form.CountryForm;
import com.dataint.diseasepo.model.form.CountryUpdateForm;
import com.dataint.diseasepo.provider.CountryProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CountryProviderFallback implements CountryProvider {
    @Value("${service.datapack.baseUrl}")
    private String baseUrl;

    @Override
    public ResultVO addCountry(CountryForm countryForm) {
        JSONObject jsonObject = GetPostUtil.sendPost(baseUrl + "country", JSONUtil.toJSon(countryForm), 8000);
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO updateCountryStatus(Integer countryId, Integer status) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("countryId",countryId);
        jsonObject.put("status",status);
        JSONObject jsonObjects = GetPostUtil.sendPut(baseUrl + "/country/status/{countryId}", JSONUtil.toJSon(jsonObject), 8000);
        return ResultVO.success(jsonObjects);
    }

    @Override
    public ResultVO updateCountry(CountryUpdateForm countryUpdateForm) {
        JSONObject jsonObject = GetPostUtil.sendPut(baseUrl + "/country", JSONUtil.toJSon(countryUpdateForm), 8000);
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO delCountry(Integer countryId) {
        JSONObject jsonObject = GetPostUtil.sendDelete(baseUrl + "/country/{countryId}");
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO getCountry(Integer countryId) {
        JSONObject jsonObject = GetPostUtil.sendGet(baseUrl + "/country/{countryId}");
        return ResultVO.success(jsonObject);
    }

    @Override
    public ResultVO getCountries(String keyword, Integer current, Integer pageSize) {
        JSONObject jsonObject = GetPostUtil.sendGet(baseUrl + "/country/all");
        return ResultVO.success(jsonObject);
    }
}
