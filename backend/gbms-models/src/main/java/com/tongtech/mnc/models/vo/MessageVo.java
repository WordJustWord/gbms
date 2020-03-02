package com.tongtech.mnc.models.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class MessageVo{
    @ApiModelProperty("消息Id")
    private Long id;

    @ApiModelProperty("消息类型")
    private int messageType;

    @ApiModelProperty("消息有效期起始时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date startTime;

    @ApiModelProperty("消息有效期截止时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date endTime;

    @ApiModelProperty("消息标题")
    private String title;

    @ApiModelProperty("内容类型，0：纯文本，1：富文本，2：原文链接")
    private int contentType;

    @ApiModelProperty("背景图片")
    private String background;

    @ApiModelProperty("原文链接")
    private String originalUrl;

    @CreationTimestamp
    private Timestamp createTime;
}
