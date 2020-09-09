package com.dataint.monitor.provider.fallback;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.monitor.provider.SysAdminProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SysAdminProviderFallback implements SysAdminProvider {
    @Value("${service.datapack.baseUrl}")
    private String baseUrl;

    @Override
    public ResultVO getUserByUniqueId(String uniqueId) {
        JSONObject jsonObject = GetPostUtil.sendGet(baseUrl + "/user");
        return ResultVO.success(jsonObject);
    }

}
