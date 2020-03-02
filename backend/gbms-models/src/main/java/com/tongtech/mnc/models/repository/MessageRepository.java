package com.tongtech.mnc.models.repository;

import com.tongtech.mnc.models.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {

    @Modifying
    @Query("update Message set deleteFlag = :deleteFlag where id = :id")
    int updateDeleteFlagById(@Param("deleteFlag") boolean deleteFlag, @Param("id") Long messageId);

    @Modifying
    @Query("update Message set status = :status where id = :id")
    int updateStatusById(@Param("status") String status, @Param("id") Long messageId);

    Optional<Message> findByIdAndDeleteFlag(Long id, boolean flag);
}
