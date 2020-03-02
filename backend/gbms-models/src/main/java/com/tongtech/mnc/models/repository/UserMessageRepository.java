package com.tongtech.mnc.models.repository;

import com.tongtech.mnc.models.domain.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserMessageRepository extends JpaRepository<UserMessage, Long>, JpaSpecificationExecutor<UserMessage> {
    List<UserMessage> findByUser_idAndMessage_idIn(Long id, List<Long> messageIds);

    @Modifying
    @Query("update UserMessage set status = :status where message.id = :messageId and user.id = :userId")
    void updateStatus(@Param("status")int status, @Param("messageId") Long messageId, @Param("userId") Long userId);

    @Modifying
    @Query("update UserMessage set isDelete = :isDelete where message.id = :messageId and user.id = :userId")
    void updateIsDelete(@Param("isDelete")boolean isDelete, @Param("messageId") Long messageId, @Param("userId") Long userId);

    List<UserMessage> findAllByMessageId(Long id);

    UserMessage findFirstByUser_idOrderByMessage_onTimeDesc(Long userId);

    UserMessage findByUser_idAndMessage_id(Long userId, Long messageId);
}
