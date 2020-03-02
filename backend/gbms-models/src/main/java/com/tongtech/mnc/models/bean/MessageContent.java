package com.tongtech.mnc.models.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "消息体对象")
public class MessageContent {

    private String content;

    @ApiModelProperty("富文本内容base64")
    private String richContent;

    @ApiModelProperty("关联场景")
    private List<Document> scenes;

    @ApiModelProperty("关联事项")
    private List<Document> matters;

    @ApiModelProperty("关联政策")
    private List<Document> policy;

    @ApiModelProperty("关联服务")
    private List<Document> services;

    @ApiModelProperty("关联问答")
    private List<Document> qa;

    @ApiModelProperty("关联文章")
    private List<Document> article;

    @ApiModelProperty("关联自定义")
    private List<CustomLink> customLinks;
}
