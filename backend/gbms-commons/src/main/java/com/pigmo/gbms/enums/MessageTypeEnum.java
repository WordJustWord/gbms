package com.pigmo.gbms.enums;

public enum MessageTypeEnum {
    ALL(-1,"所有类型"),
    SYS_NOTIFICATION(1,"系统通知");

    private int code;
    private String text;

    MessageTypeEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }
}
