package com.tongtech.mnc.models.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
public class UserMessageDTO {
    private Long id;

    @ApiModelProperty("用户")
    private UserDTO user;

    @ApiModelProperty("消息")
    private MessageDTO message;

    @ApiModelProperty("阅读状态，0：未读，1：已读")
    private int status;

    @CreationTimestamp
    private Timestamp createTime;
}
