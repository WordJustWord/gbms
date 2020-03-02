package com.tongtech.mnc.models.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MessageWithContentVo extends MessageVo{
    @ApiModelProperty("MessageContent对象json字符串")
    private String messageContent;
}
