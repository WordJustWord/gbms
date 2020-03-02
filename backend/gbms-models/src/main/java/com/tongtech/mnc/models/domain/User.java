package com.tongtech.mnc.models.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="mnc_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ApiModelProperty("用户类型")
    @Column(name = "user_type")
    private int userType;

    @ApiModelProperty("用户唯一标识(个人：身份证号码，企业：社会信用代码)，多个用逗号分隔")
    @Column(name = "unique_code")
    private String uniqueCode;

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;
}
