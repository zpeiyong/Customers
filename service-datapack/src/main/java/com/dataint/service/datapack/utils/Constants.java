package com.dataint.service.datapack.utils;

import com.dataint.service.datapack.db.entity.Country;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    /* 疫情事件状态对应键值对 */
    public static final Map<String, String> eventStatusMap = new HashMap<String, String>() {{
        put("SUBMITTED", "监测提交");
        put("RUNNING", "监测中");
        put("STOPPED", "监测停止");
        put("FAILED", "监测故障");
    }};

    /* 舆情文章内容类别 */
    public static final Map<String, String> articleTypeMap = new HashMap<String, String>() {{
        put("statistic", "报告");
        put("pubinfo", "新闻");
        put("other", "其他");
    }};

    /* 重点关注(直达地区)国家map<countryId, Country> */
    public static Map<Long, Country> focusCountryMap = new HashMap<>();
}
