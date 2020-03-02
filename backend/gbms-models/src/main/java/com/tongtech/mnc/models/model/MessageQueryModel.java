package com.tongtech.mnc.models.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MessageQueryModel {
    @ApiModelProperty("终端类型：1-PC端，2-音箱，3-自助终端，4-机器人，5-微信端，6-APP ,7-微信小程序，8-支付宝小程序 ")
    private String clientType = "1";

    @ApiModelProperty("行政区划")
    private String region;

    @ApiModelProperty("用户类型 0-个人|1-法人|-1-全部")
    @NotNull
    private String userType;

    @ApiModelProperty("用户Id")
    @NotNull
    private String userId;

    @ApiModelProperty("开始时间 yyy-MM-dd格式,从0点开始")
    private String startTime;

    @ApiModelProperty("结束时间yyy-MM-dd格式,到23:59:59结束")
    private String endTime;

    @ApiModelProperty("状态 0-已读|1-未读|-1-全部")
    private int state = -1;

    @ApiModelProperty("消息类型 -1-全部")
    private int messageType = -1;
}
