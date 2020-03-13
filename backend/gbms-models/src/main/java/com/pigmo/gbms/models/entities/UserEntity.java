package com.pigmo.gbms.models.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户信息表
 */
@Entity
@Table(name = "user")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ApiModelProperty("用户编码")
    @Column(name = "user_code")
    private String userCode;
    @ApiModelProperty("用户名")
    @Column(name = "user_name")
    private String userName;
    @ApiModelProperty("密码")
    @Column(name = "user_pwd")
    private String userPwd;
    @ApiModelProperty("真实姓名")
    @Column(name = "real_name")
    private String realName;
    @ApiModelProperty("电子邮箱")
    private String email;
    @ApiModelProperty("性别")
    private int gender;
    @ApiModelProperty("用户状态，0：未激活；1：已激活；2：已注销")
    private int status;
    @ApiModelProperty("用户类型，0：普通用户；1：管理员；2：其他")
    @Column(name = "user_type")
    private int userType;
    @ApiModelProperty("创建时间")
    private Date ctime;
    @ApiModelProperty("修改时间")
    private Date etime;
    @ApiModelProperty("创建者")
    private Long creator;
    @ApiModelProperty("操作者")
    private Long operator;
}
