package com.pigmo.gbms.models.service;

import com.pigmo.gbms.models.entities.UserEntity;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> getUser(String userName);
}
