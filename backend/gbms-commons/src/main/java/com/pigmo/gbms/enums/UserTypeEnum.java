package com.pigmo.gbms.enums;

/**
 * 用户类型
 */
public enum UserTypeEnum {

    PERSONAL(0, "个人"),
    ENTERPRISE(1, "法人");

    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String text;

    UserTypeEnum(Integer code, String text) {
        this.code = code;
        this.text = text;
    }
}
