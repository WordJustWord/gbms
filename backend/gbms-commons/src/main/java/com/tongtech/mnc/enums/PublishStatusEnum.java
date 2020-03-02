package com.tongtech.mnc.enums;

public enum PublishStatusEnum {
    ALL(-1,"不限"),
    WAITING_PUBLISH(0,"未发布"),
    PUBLISHING(1,"发布中"),
    EXPIRED(2,"已过期")
    ;

    private int code;
    private String text;

    PublishStatusEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }
}
