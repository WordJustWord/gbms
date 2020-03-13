package com.pigmo.gbms.models.service.impl;

import com.pigmo.gbms.constants.CommonContant;
import com.pigmo.gbms.models.entities.UserEntity;
import com.pigmo.gbms.models.repository.UserRepository;
import com.pigmo.gbms.models.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Override
    public Optional<UserEntity> getUser(String userName) {
        return repository.findByUserNameAndStatus(userName, CommonContant.STATUS_ACTIVE);
    }

}
