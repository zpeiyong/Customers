package com.dataint.topic.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.dataint.topic.model.PageParam;
import com.dataint.topic.model.ResponseStatus;

/**
 * <p>.</p>
 *
 * @author Magic Joey
 * @version ResultUtil.java 1.0 Created@2019-06-10 10:06 $
 */
public class ResultUtil {

    private static final String REQUEST_SUCCESS = "请求成功";

    private ResultUtil() {

    }

    public static Map<String, Object> buildSuccResultMap() {
        return buildSuccResultMap("");
    }

    public static Map<String, Object> buildSuccResultMap(String msg) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("status", ResponseStatus.SUCCESS.getCode());
        dataMap.put("msg", StringUtils.isEmpty(msg) ? REQUEST_SUCCESS : msg);
        return dataMap;
    }

    public static Map<String, Object> buildSuccResultMap(Object data) {
        return buildSuccResultMap(data, "");
    }

    public static Map<String, Object> buildSuccResultMap(Object data, String msg) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("data", data);
        dataMap.put("status", ResponseStatus.SUCCESS.getCode());
        dataMap.put("msg", StringUtils.isEmpty(msg) ? REQUEST_SUCCESS : msg);
        return dataMap;
    }

    public static Map<String, Object> buildSuccResultMap(Object data, PageParam pageParam,
                                                         Long total) {
        return buildSuccResultMap(data, pageParam.getCurrentPage(), pageParam.getPageSize(), total,
            "");
    }

    public static Map<String, Object> buildSuccResultMap(Object data, Integer current,
                                                         Integer pageSize, Long total) {
        return buildSuccResultMap(data, current, pageSize, total, "");
    }

    public static Map<String, Object> buildSuccResultMap(Object data, Integer current,
                                                         Integer pageSize, Long total, String msg) {
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> pageMap = new HashMap<>();
        dataMap.put("status", ResponseStatus.SUCCESS.getCode());
        dataMap.put("msg", StringUtils.isEmpty(msg) ? REQUEST_SUCCESS : msg);
        dataMap.put("data", data);
        pageMap.put("total", total);
        pageMap.put("current", current);
        pageMap.put("pageSize", pageSize);
        dataMap.put("pagination", pageMap);
        return dataMap;
    }

    public static Map<String, Object> buildFailMap(String msg) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("status", ResponseStatus.FAIL.getCode());
        dataMap.put("msg", msg);
        return dataMap;
    }
}
