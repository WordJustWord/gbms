package com.pigmo.gbms.enums;

public enum  ResultStatusEnum {

    SUCCESS("10000","成功"),
    NOT_FOUND("10001","未找到"),
    FAILED("10002","失败"),
    UNKNOWN("10003","未知");

    private String code;
    private String message;

    ResultStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
