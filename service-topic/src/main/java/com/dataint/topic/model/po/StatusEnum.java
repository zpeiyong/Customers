package com.dataint.topic.model.po;

import lombok.Getter;

/**
 * <p>.</p>
 *
 * @author Magic Joey
 * @version StatusEnum.java 1.0 Created@2019-06-13 23:40 $
 */
@Getter
public enum StatusEnum {

    YES("Y", "已处理"),
    NO("N", "未处理");

    private String code;

    private String description;

    StatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
