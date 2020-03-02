package com.tongtech.mnc.enums;

public enum ContentTypeEnum {
    ALL(-1,"所有类型"),
    TEXT(0,"纯文本"),
    RICH_TEXT(1,"富文本"),
    ORIGINAL_URL(2,"原文链接")
    ;

    private int code;
    private String text;

    ContentTypeEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }
}
