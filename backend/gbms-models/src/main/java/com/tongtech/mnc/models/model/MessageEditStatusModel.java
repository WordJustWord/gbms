package com.tongtech.mnc.models.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MessageEditStatusModel {
    @ApiModelProperty("消息id")
    @NotNull
    private Long messageId;

    @ApiModelProperty("用户类型 0-个人|1-法人|-1-全部")
    @NotNull
    private String userType;

    @ApiModelProperty("用户Id")
    @NotNull
    private String userId;
}
