package com.pigmo.gbms.controller.base;

import com.pigmo.gbms.security.bean.AuthenticationInfo;
import com.pigmo.gbms.security.bean.AuthorizationUser;
import com.pigmo.gbms.security.bean.JwtUser;
import com.pigmo.gbms.security.utils.JwtTokenUtil;
import com.pigmo.gbms.utils.EncryptUtils;
import com.pigmo.gbms.utils.ResponseResult;
import com.pigmo.gbms.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("auth")
@Api(value = "认证", tags = "认证登录")
public class AuthenticationController {

    @Value("${jwt.header}")
    private String tokenHeader;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserDetailsService userDetailsService;

    public AuthenticationController(JwtTokenUtil jwtTokenUtil, @Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * 登录授权
     *
     * @param authorizationUser
     * @return
     */
    @ApiOperation(value = "用户登录")
    @ApiImplicitParam(name = "Authorization", required = false, paramType = "header", value = "用户登录接口不传token")
    @PostMapping(value = "${jwt.auth.path}")
    public ResponseEntity<ResponseResult<AuthenticationInfo>> login(@Validated @RequestBody AuthorizationUser authorizationUser) {

        final JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(authorizationUser.getUsername());

        if (!jwtUser.getPassword().equals(EncryptUtils.encryptPassword(authorizationUser.getPassword()))) {
            throw new AccountExpiredException("密码错误");
        }

        if (!jwtUser.isEnabled()) {
            throw new AccountExpiredException("账号已停用，请联系管理员");
        }

        // 生成令牌
        final String token = jwtTokenUtil.generateToken(jwtUser);

        // 返回 token
        return ResponseResult.ok(new AuthenticationInfo(token, jwtUser));
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @ApiOperation(value = "获取用户信息")
    @GetMapping(value = "${jwt.auth.account}")
    public ResponseEntity<ResponseResult<JwtUser>> getUserInfo() {
        return ResponseResult.ok((JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername()));
    }
}
