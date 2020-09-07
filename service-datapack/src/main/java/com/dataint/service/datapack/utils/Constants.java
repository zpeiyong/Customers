package com.dataint.service.datapack.utils;

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
}
