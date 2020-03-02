package com.tongtech.mnc.enums;

public enum  ResultStatusEnum {

    SUCCESS(10000,"成功"),
    NOT_FOUND(10001,"未找到"),
    FAILED(10002,"失败"),
    UNKNOW(10003,"未知");

    private int code;
    private String test;

    ResultStatusEnum(int code, String test) {
        this.code = code;
        this.test = test;
    }

    public int getCode() {
        return code;
    }
}
