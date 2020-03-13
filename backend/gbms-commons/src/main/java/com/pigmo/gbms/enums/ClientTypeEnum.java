package com.pigmo.gbms.enums;

public enum ClientTypeEnum {
    ALL(-1,"所有类型"),
    PC(1,"PC端"),
    SPEAKER_BOX(2,"音箱"),
    SELF_SERVICE_TERMINAL(3,"自助终端"),
    ROBOT(4,"机器人"),
    WECHAT(5,"微信"),
    APP(6,"APP"),
    WECHAT_MINI_PROGRAM(7,"微信小程序"),
    ALIPAY_MINI_PROGRAM(8,"支付宝小程序")
    ;

    private int code;
    private String text;

    ClientTypeEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }
}
