package com.dataint.topic.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>.</p>
 *
 * @author Magic Joey
 * @version ParamUtil.java 1.0 Created@2019-06-25 11:26 $
 */
public class ParamUtil {

    private ParamUtil(){
        
    }

    public static Map<String, String> buildReqMap(String key, Object param) {
        Map<String,String> hashMap = new HashMap<>(1);
        hashMap.put(key, param.toString());
        return hashMap;
    }
}
