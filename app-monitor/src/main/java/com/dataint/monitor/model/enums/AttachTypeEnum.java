package com.dataint.monitor.model.enums;

public enum AttachTypeEnum {

    IMAGE("image", "图片"),
    FILE("file", "普通附件");

    private String code;

    private String description;

    AttachTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
