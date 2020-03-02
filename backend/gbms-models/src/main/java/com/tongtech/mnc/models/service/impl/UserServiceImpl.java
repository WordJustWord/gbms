package com.tongtech.mnc.models.service.impl;

import com.google.common.collect.Lists;
import com.tongtech.mnc.enums.UserTypeEnum;
import com.tongtech.mnc.models.domain.User;
import com.tongtech.mnc.models.repository.UserRepository;
import com.tongtech.mnc.models.service.UserService;
import com.tongtech.mnc.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> save(String personalUniqueCodes, String enterpriseUniqueCodes) {
        String[] userArray = personalUniqueCodes.split(",");
        String[] enterpriseArray = enterpriseUniqueCodes.split(",");
        List<User> users = resolveUser(userArray, UserTypeEnum.PERSONAL);
        List<User> enterprises = resolveUser(enterpriseArray, UserTypeEnum.ENTERPRISE);
        List<User> userResult = userRepository.findByUserTypeAndUniqueCodeIn(UserTypeEnum.PERSONAL.getCode(), userArray);
        List<User> enterpriseResult = userRepository.findByUserTypeAndUniqueCodeIn(UserTypeEnum.ENTERPRISE.getCode(), enterpriseArray);
        if (userResult.size() > 0) {
            userResult.forEach(x->users.removeIf(u->u.getUniqueCode().equals(x.getUniqueCode())));
        }
        if (enterpriseResult.size() > 0) {
            enterpriseResult.forEach(x->enterprises.removeIf(u->u.getUniqueCode().equals(x.getUniqueCode())));
        }
        users.addAll(enterprises);
        List<User> userSaveResult = userRepository.saveAll(users);
        userSaveResult.addAll(userResult);
        userSaveResult.addAll(enterpriseResult);
        return userSaveResult;
    }

    private List<User> resolveUser(String[] arrays, UserTypeEnum userType) {
        List<User> users = Lists.newArrayList();
        if (arrays.length > 0) {
            for (String x : arrays) {
                User user = new User();
                user.setUserType(userType.getCode());
                user.setUniqueCode(x);
                Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
                user.setCreateTime(timestamp);
                users.add(user);
            }
        }
        return users;
    }
}
