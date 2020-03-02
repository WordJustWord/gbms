package com.tongtech.mnc.models.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
public class UserDTO {

    private Long id;

    @ApiModelProperty("用户类型")
    private int userType;

    @ApiModelProperty("用户唯一标识(个人：身份证号码，企业：社会信用代码)，多个用逗号分隔")
    private String uniqueCode;

    @CreationTimestamp
    private Timestamp createTime;
}
