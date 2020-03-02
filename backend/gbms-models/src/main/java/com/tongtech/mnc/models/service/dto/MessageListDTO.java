package com.tongtech.mnc.models.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.util.Date;

@Data
public class MessageListDTO {

    @ApiModelProperty("消息ID")
    private Long id;

    @ApiModelProperty("消息标题")
    private String title;

    @ApiModelProperty("消息类型")
    private int messageType;

    @ApiModelProperty("发布状态")
    private int publishStatus;

    @ApiModelProperty("用户类型")
    private String UserType;

    @ApiModelProperty("接收范围，0：全部用户，1：指定用户，2：行政区划")
    private int receivingRange;

    @ApiModelProperty("所有用户")
    private boolean allUserSub;

    @ApiModelProperty("行政区划")
    private String regions;

    @ApiModelProperty("内容类型，0：纯文本，1：富文本，2：原文链接")
    private int contentType;

    @ApiModelProperty("创建时间")
    private Timestamp createTime;

    @ApiModelProperty("修改时间")
    private Timestamp updateTime;

    @ApiModelProperty("上架状态")
    private String status;
}
