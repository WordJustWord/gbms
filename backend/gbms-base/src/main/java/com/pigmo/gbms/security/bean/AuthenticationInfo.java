package com.pigmo.gbms.security.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class AuthenticationInfo implements Serializable {
    private final String token;
    private final JwtUser user;
}
