package com.tongtech.mnc.models.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "mnc_message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ApiModelProperty("终端类型类型")
    @Column(name = "client_type")
    private String clientType;

    @ApiModelProperty("消息类型")
    @Column(name = "message_type")
    private int messageType;

    @ApiModelProperty("消息有效期起始时间")
    @Column(name = "start_time")
    private Date startTime;

    @ApiModelProperty("消息有效期截止时间")
    @Column(name = "end_time")
    private Date endTime;

    @ApiModelProperty("全部用户")
    @Column(name = "is_all_user_sub")
    private boolean allUserSub;

    @ApiModelProperty("接收范围，0：全部用户，1：指定用户，2：行政区划")
    @Column(name = "receiving_range")
    private int receivingRange;

    @ApiModelProperty("行政区划")
    private String regions;

    @ApiModelProperty("用户类型")
    private String userType;

    @ApiModelProperty("个人用户唯一标识(身份证号码)，多个用逗号分隔")
    @Column(name = "p_unique_codes", columnDefinition = "longtext")
    private String personalUniqueCodes;

    @ApiModelProperty("企业用户唯一标识(社会信用代码)，多个用逗号分隔")
    @Column(name = "e_unique_codes", columnDefinition = "longtext")
    private String enterpriseUniqueCodes;

    @ApiModelProperty("消息标题")
    private String title;

    @ApiModelProperty("内容类型，0：纯文本，1：富文本，2：原文链接")
    @Column(name = "content_type")
    private int contentType;

    @Lob
    @ApiModelProperty("背景图片")
    @Column(columnDefinition = "longtext")
    private String background;

    @ApiModelProperty("原文链接")
    @Column(name = "original_url")
    private String originalUrl;

    @Lob
    @ApiModelProperty("MessageContent对象json字符串")
    @Column(columnDefinition = "longtext")
    private String messageContent;

    @ApiModelProperty("上下架状态，0：下架状态，1：上架状态")
    private String status;

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updateTime;

    @ApiModelProperty("删除标识，0：正常状态，1：删除状态")
    @Column(name = "delete_flag")
    private boolean deleteFlag;

    @ApiModelProperty("上架时间")
    @Column(name = "on_time")
    private Timestamp onTime;
}
