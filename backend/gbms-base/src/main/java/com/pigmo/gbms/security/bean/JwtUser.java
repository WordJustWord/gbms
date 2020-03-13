package com.pigmo.gbms.security.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Getter
@AllArgsConstructor
public class JwtUser implements UserDetails {

    @JsonIgnore
    private final Long id;
    @ApiModelProperty("用户编码")
    private final String userCode;
    @ApiModelProperty("用户名")
    private final String userName;
    @ApiModelProperty("密码")
    @JsonIgnore
    private final String userPwd;
    @ApiModelProperty("真实姓名")
    private final String realName;
    @ApiModelProperty("电子邮箱")
    private final String email;
    @ApiModelProperty("性别")
    private final int gender;
    @ApiModelProperty("用户状态，0：未激活；1：已激活；2：已注销")
    private final int status;
    @ApiModelProperty("用户类型，0：普通用户；1：管理员；2：其他")
    private final int userType;

    @JsonIgnore
    private final Date lastPasswordResetDate;

    @JsonIgnore
    private final Collection<GrantedAuthority> authorities;

    @Override
    @JsonIgnore
    public String getPassword() {
        return userPwd;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return userName;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
