package com.dataint.diseasepo.provider;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.ResultVO;


public interface SysAdminProvider {
    /*
    获取用户信息
     */

    ResultVO<JSONObject> getUserByUniqueId(String uniqueId);
}
