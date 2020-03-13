package com.pigmo.gbms.enums;

/**
 * 消息上下架状态
 */
public enum MessageStatus {

    PUBLISH("1","消息上架"),
    WITHDRAW("0","消息下架");

    private String code;
    private String text;

    public String getCode() {
        return code;
    }

    MessageStatus(String code,String text){
        this.code = code;
        this.text = text;
    }
}
