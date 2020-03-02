package com.tongtech.mnc.models.service;

import com.tongtech.mnc.models.domain.Message;
import com.tongtech.mnc.models.domain.User;
import com.tongtech.mnc.models.domain.UserMessage;
import com.tongtech.mnc.models.model.MessageEditStatusModel;
import com.tongtech.mnc.models.model.MessageQueryModel;
import com.tongtech.mnc.models.vo.Result;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserMessageService {
    Result getNoticeMessage(MessageQueryModel messageQueryModel, Pageable pageable);

    Result editMessageStatus(MessageEditStatusModel messageEditStatusModel);

    Result deleteMessage(MessageEditStatusModel messageEditStatusModel);

    List<UserMessage> saveUserMessages(List<User> user, Message message);

    /**
     * 获取通知消息详情
     * @param messageEditStatusModel
     * @return
     */
    Result getNoticeMessageDetail(MessageEditStatusModel messageEditStatusModel);
}
