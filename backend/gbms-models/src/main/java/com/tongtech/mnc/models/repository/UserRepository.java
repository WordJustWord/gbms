package com.tongtech.mnc.models.repository;

import com.tongtech.mnc.models.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByUserTypeAndUniqueCode(int userType, String uniqueCode);
    List<User> findByUserTypeAndUniqueCodeIn(int userType, String[] s);
}
