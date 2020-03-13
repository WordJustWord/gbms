package com.pigmo.gbms.security.bean;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AuthorizationUser {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Override
    public String toString() {
        return "{username=" + username  + ", password= ******}";
    }
}
