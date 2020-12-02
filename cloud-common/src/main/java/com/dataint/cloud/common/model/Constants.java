package com.dataint.cloud.common.model;

import java.text.SimpleDateFormat;

public class Constants {

    // 认证头Key
    public final static String AUTHORIZE_TOKEN = "Authorization";
    public final static String AUTHORIZE_ACCESS_TOKEN = "token";
    public final static String X_CLIENT_TOKEN = "x-client-token";
    public final static String X_CLIENT_TOKEN_USER = "x-client-token-user";
    public final static String X_CLIENT_TOKEN_RESOURCEIDS = "x-client-token-resourceIds";
    public final static String X_CLIENT_TOKEN_ROLES = "x-client-token-roles";

    // JWT加密
    public final static String JWT_KEY = "dataint";
    public final static String SESSION_USER = "USER_DATAINT";
    public final static Long TOKEN_EXPIRE_TIME = 60 * 60 * 4L;  // 超时时间(分钟)

    /* 日期格式\时间格式 */
    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();
    public final static String DateFormat = "yyyy-MM-dd";
    public final static SimpleDateFormat DateSDF = new SimpleDateFormat(DateFormat);
    public static SimpleDateFormat getDateFormat() {
        SimpleDateFormat sdf = threadLocal.get();
        if (sdf == null){
            sdf = new SimpleDateFormat(DateFormat);
        }
        return sdf;
    }
    public final static String DateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    public final static SimpleDateFormat DateTimeSDF = new SimpleDateFormat(DateTimeFormat);
    public static SimpleDateFormat getDateTimeFormat() {
        SimpleDateFormat sdf = threadLocal.get();
        if (sdf == null){
            sdf = new SimpleDateFormat(DateTimeFormat);
        }
        return sdf;
    }

    /* 多元素列表或多元素字符串分割器 */
    public static final String JOINER = "|";

    public static final String SPLITTER = "\\|";



}
