package com.pigmo.gbms.security.service;

import com.pigmo.gbms.exception.BadRequestException;
import com.pigmo.gbms.models.entities.UserEntity;
import com.pigmo.gbms.models.service.UserService;
import com.pigmo.gbms.security.bean.JwtUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        Optional<UserEntity> user = userService.getUser(userName);
        if (!user.isPresent()) {
            throw new BadRequestException("用户不存在或未激活");
        }
        return createJwtUser(user.get());
    }

    private UserDetails createJwtUser(UserEntity entity) {
        return new JwtUser(
                entity.getId(),
                entity.getUserCode(),
                entity.getUserName(),
                entity.getUserPwd(),
                entity.getRealName(),
                entity.getEmail(),
                entity.getGender(),
                entity.getStatus(),
                entity.getUserType(),
                entity.getEtime(),
                null
        );
    }

}
