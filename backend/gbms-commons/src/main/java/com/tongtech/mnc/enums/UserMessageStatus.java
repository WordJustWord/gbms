package com.tongtech.mnc.enums;

/**
 * 阅读状态
 */
public enum UserMessageStatus {

    READED(1,"已读"),
    NOT_READ(0,"未读");

    private int code;
    private String text;

    public int getCode() {
        return code;
    }

    UserMessageStatus(int code, String text){
        this.code = code;
        this.text = text;
    }
}
