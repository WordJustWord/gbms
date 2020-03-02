package com.tongtech.mnc.models.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Document {

    @ApiModelProperty("编号")
    private String id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("行政区划")
    private String regions;
}
