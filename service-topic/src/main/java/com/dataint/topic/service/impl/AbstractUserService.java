package com.dataint.topic.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dataint.topic.utils.BasicConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>.</p>
 *
 * @author Magic Joey
 * @version AbstractUserService.java 1.0 Created@2019-06-27 17:46 $
 */
abstract class AbstractUserService implements IUserService, BasicConstant {

    Map<Integer, JSONObject> buildRespMap(JSONObject jsonObject, String key) {
        Map<Integer, JSONObject> articleMap = new HashMap<>();
        for (Object obj : jsonObject.getJSONArray("data")) {
            if (obj instanceof JSONObject) {
                JSONObject jsonObj = (JSONObject) obj;
                articleMap.put(jsonObj.getInteger(key), jsonObj);
            }
        }
        return articleMap;
    }
}
