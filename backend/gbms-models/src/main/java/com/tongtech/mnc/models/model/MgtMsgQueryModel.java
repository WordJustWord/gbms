package com.tongtech.mnc.models.model;

import com.tongtech.mnc.enums.ContentTypeEnum;
import com.tongtech.mnc.enums.MessageTypeEnum;
import com.tongtech.mnc.enums.PublishStatusEnum;
import com.tongtech.mnc.enums.RecevingRangeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MgtMsgQueryModel {


    @ApiModelProperty("消息标题")
    private String title;

    @ApiModelProperty("消息类型")
    private int messageType = MessageTypeEnum.ALL.getCode();

    @ApiModelProperty("用户类型")
    private String userType;

    @ApiModelProperty("发布状态，0：未发布，1：已发布，2：已过期")
    private int publishStatus = PublishStatusEnum.ALL.getCode();

    @ApiModelProperty("接受范围")
    private int range = RecevingRangeEnum.ALL.getCode();

    @ApiModelProperty("内容类型，0：纯文本，1：富文本，2：原文链接")
    private int contentType = ContentTypeEnum.ALL.getCode();

    @ApiModelProperty("终端类型类型")
    private String clientType;

    @ApiModelProperty("上下架状态")
    private String status;
}
