package com.tongtech.mnc.models.service;

import com.tongtech.mnc.models.domain.User;

import java.util.List;

public interface UserService {
    List<User> save(String personalUniqueCodes,String enterpriseUniqueCodes);
}
