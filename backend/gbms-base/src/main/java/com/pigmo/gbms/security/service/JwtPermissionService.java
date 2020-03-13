package com.pigmo.gbms.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JwtPermissionService {
    /**
     * key的名称如有修改，请同步修改 UserServiceImpl 中的 update 方法
     * @param user
     * @return
     */
//    public Collection<GrantedAuthority> mapToGrantedAuthorities(User user) {
//        List<Role> roles = user.getRoles();
//        return roles.stream().flatMap(role -> role.getPermissions().stream())
//                .map(permission -> new SimpleGrantedAuthority(permission.getPermissionName()))
//                .collect(Collectors.toList());
//    }
}
