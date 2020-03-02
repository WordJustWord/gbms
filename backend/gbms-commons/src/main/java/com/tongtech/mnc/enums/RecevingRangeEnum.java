package com.tongtech.mnc.enums;

/**
 * 接收范围
 */
public enum RecevingRangeEnum {
    ALL(-1,"所有"),
    ALL_USERS(0,"全部用户"),
    SPEC_USERS(1,"指定用户"),
    ADMIN_AREA(2,"行政区划");

    private int code;
    private String text;

    RecevingRangeEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }
}
