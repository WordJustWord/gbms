package com.tongtech.mnc.models.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;

@Data
public class MessageDTO {
    private Long id;

    @ApiModelProperty("终端类型类型")
    @NotNull
    private String clientType;

    @ApiModelProperty("消息类型")
    @NotNull
    private int messageType;

    @ApiModelProperty("消息有效期起始时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd hh:mm:ss")
    @NotNull
    private Date startTime;

    @ApiModelProperty("消息有效期截止时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd hh:mm:ss")
    @NotNull
    private Date endTime;

    @ApiModelProperty("接收范围，0：全部用户，1：指定用户，2：行政区划")
    @NotNull
    private int receivingRange;

    @ApiModelProperty("全部用户")
    @NotNull
    private boolean allUserSub;

    @ApiModelProperty("行政区划")
    private String regions;

    @ApiModelProperty("用户类型")
    @NotNull
    private String userType;

    @ApiModelProperty("个人用户唯一标识(身份证号码)，多个用逗号分隔")
    @Pattern(regexp = "^$|^[0-9a-zA-Z,]+$",message = "格式不正确，输入身份证号码，多个用英文逗号分隔")
    private String personalUniqueCodes;

    @ApiModelProperty("企业用户唯一标识(社会信用代码)，多个用逗号分隔")
    @Pattern(regexp = "^$|^[0-9a-zA-Z,]+$",message = "格式不正确，输入社会信用代码，多个用英文逗号分隔")
    private String enterpriseUniqueCodes;

    @ApiModelProperty("消息标题")
    @NotNull
    @Size(max = 90,message = "消息标题的最大长度为90")
    private String title;

    @ApiModelProperty("内容类型，0：纯文本，1：富文本，2：原文链接")
    @NotNull
    private int contentType;

    @ApiModelProperty("背景图片")
    private String background;

    @ApiModelProperty("原文链接")
    private String originalUrl;

    @ApiModelProperty("MessageContent对象json字符串")
    private String messageContent;

    @ApiModelProperty("上下架状态，0：下架状态，1：上架状态")
    @NotNull
    private String status;

    @CreationTimestamp
    private Timestamp createTime;

    @UpdateTimestamp
    private Timestamp updateTime;

}
