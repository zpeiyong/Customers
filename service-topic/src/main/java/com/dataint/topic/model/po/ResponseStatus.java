package com.dataint.topic.model.po;

import lombok.Getter;

/**
 * <p>.</p>
 *
 * @author Magic Joey
 * @version ResponseStatus.java 1.0 Created@2019-06-26 14:53 $
 */
@Getter
public enum ResponseStatus {

    SUCCESS("S", "成功"),
    FAIL("F", "失败");

    private String code;

    private String description;

    ResponseStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static boolean isSuccess(String code){
        return SUCCESS.getCode().equals(code);
    }
}
