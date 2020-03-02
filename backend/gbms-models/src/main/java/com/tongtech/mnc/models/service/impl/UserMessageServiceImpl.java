package com.tongtech.mnc.models.service.impl;

import cn.hutool.core.date.DateTime;
import com.google.common.collect.Lists;
import com.tongtech.mnc.enums.MessageStatus;
import com.tongtech.mnc.enums.UserMessageStatus;
import com.tongtech.mnc.models.domain.Message;
import com.tongtech.mnc.models.domain.User;
import com.tongtech.mnc.models.domain.UserMessage;
import com.tongtech.mnc.models.model.MessageEditStatusModel;
import com.tongtech.mnc.models.model.MessageQueryModel;
import com.tongtech.mnc.models.repository.MessageRepository;
import com.tongtech.mnc.models.repository.UserMessageRepository;
import com.tongtech.mnc.models.repository.UserRepository;
import com.tongtech.mnc.models.service.MessageService;
import com.tongtech.mnc.models.service.UserMessageService;
import com.tongtech.mnc.models.service.mapper.MessageMapper;
import com.tongtech.mnc.models.service.mapper.UserMessageMapper;
import com.tongtech.mnc.models.vo.MessageVo;
import com.tongtech.mnc.models.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@Transactional
public class UserMessageServiceImpl implements UserMessageService {

    @Autowired
    UserMessageRepository userMessageRepository;
    @Autowired
    MessageService messageService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMessageMapper userMessageMapper;
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    MessageRepository messageRepository;

    @Override
    public Result getNoticeMessage(MessageQueryModel messageQueryModel, Pageable pageable) {
        try {
            //时间校验
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //如果入参开始时间大于当前时间，则返回空列表
            if(sdf.parse(messageQueryModel.getStartTime()).after(DateTime.now())){
                return Result.success(null, "没有消息数据！");
            }
        }catch (Exception e){
            log.error("获取通知消息列表时间转化错误 startTime={} endTime={}", messageQueryModel.getStartTime(), messageQueryModel.getEndTime());
        }

        //查询符合条件的message并入库
        User user = insertMessage(messageQueryModel);
        if(user == null){
            return Result.success(null, "没有消息数据！");
        }
        //查询message
        Page<UserMessage> page = userMessageRepository.findAll(new Spec(messageQueryModel, user.getId()), pageable);
        if(page.getContent() != null){
            List<UserMessage> content = page.getContent();
            List<MessageVo> messageVos = new ArrayList<>(content.size());
            content.forEach(userMessage ->{
                messageVos.add(messageMapper.toVo(userMessage.getMessage()));
            });
            return Result.success(new PageImpl<>(messageVos == null ? new ArrayList<>() : messageVos, pageable, page.getTotalElements()), "获取成功！");
        }else {
            return Result.success(null, "没有消息数据！");
        }
    }

    @Override
    public Result getNoticeMessageDetail(MessageEditStatusModel messageEditStatusModel) {
        User user = userRepository.findByUserTypeAndUniqueCode(NumberUtils.toInt(messageEditStatusModel.getUserType()), messageEditStatusModel.getUserId());
        if(user == null){
            return Result.notFound(null, "用户不存在！");
        }
        UserMessage userMessage = userMessageRepository.findByUser_idAndMessage_id(user.getId(), messageEditStatusModel.getMessageId());
        if(userMessage == null){
            return Result.notFound(null, "消息不存在！");
        }
        return Result.success(messageMapper.toMessageWithContentVo(userMessage.getMessage()), "获取成功");
    }

    @Override
    public Result editMessageStatus(MessageEditStatusModel messageEditStatusModel) {
        User user = userRepository.findByUserTypeAndUniqueCode(NumberUtils.toInt(messageEditStatusModel.getUserType()), messageEditStatusModel.getUserId());
        Result result = getResult(messageEditStatusModel, user);
        if (result != null){
            return result;
        }
        userMessageRepository.updateStatus(UserMessageStatus.READED.getCode(), messageEditStatusModel.getMessageId(), user.getId());
        return Result.success(null, "修改成功！");
    }

    @Override
    public Result deleteMessage(MessageEditStatusModel messageEditStatusModel) {
        User user = userRepository.findByUserTypeAndUniqueCode(NumberUtils.toInt(messageEditStatusModel.getUserType()), messageEditStatusModel.getUserId());
        Result result = getResult(messageEditStatusModel, user);
        if (result != null){
            return result;
        }
        userMessageRepository.updateIsDelete(true, messageEditStatusModel.getMessageId(), user.getId());
        return Result.success(null, "删除成功！");
    }

    private Result getResult(MessageEditStatusModel messageEditStatusModel, User user) {
        if(user == null){
            return Result.notFound(null, "用户不存在！");
        }
        Optional<Message> messageOptional = messageRepository.findById(messageEditStatusModel.getMessageId());
        if(!messageOptional.isPresent()){
            return Result.notFound(null, "消息不存在！");
        }
        return null;
    }

    /**
     * 查询符合条件的message并入库
     * @param messageQueryModel
     */
    private User insertMessage(MessageQueryModel messageQueryModel) {
        //查询用户Id
        User user = userRepository.findByUserTypeAndUniqueCode(NumberUtils.toInt(messageQueryModel.getUserType()), messageQueryModel.getUserId());
        //查询此用户消息最大的上架时间
        Timestamp maxOnTime = null;
        if(user != null){
            UserMessage maxOnTimeUserMessage = userMessageRepository.findFirstByUser_idOrderByMessage_onTimeDesc(user.getId());
            maxOnTime = maxOnTimeUserMessage == null ? null : maxOnTimeUserMessage.getMessage().getOnTime();
        }
        List<Message> messages = messageService.findAll(messageQueryModel, maxOnTime);
        if(!messages.isEmpty()){
            //查询用户
            if(user == null){
                user = new User();
                user.setUniqueCode(messageQueryModel.getUserId());
                user.setUserType(NumberUtils.toInt(messageQueryModel.getUserType()));
                user.setCreateTime(new Timestamp(System.currentTimeMillis()));
                userRepository.save(user);
            }
            List<UserMessage> userMessages = new ArrayList<>(messages.size());
            for (Message message : messages) {
                UserMessage userMessage = new UserMessage();
                userMessage.setUser(user);
                userMessage.setMessage(message);
                userMessage.setStatus(UserMessageStatus.NOT_READ.getCode());
                userMessage.setCreateTime(new Timestamp(System.currentTimeMillis()));
                userMessages.add(userMessage);
            }
            userMessageRepository.saveAll(userMessages);
        }
        return user;
    }

    @Override
    public List<UserMessage> saveUserMessages(List<User> user, Message message) {
        if (user.size()<=0){
            return Lists.newArrayList();
        }
        //find exists user message
        List<UserMessage> existsUserMsg = userMessageRepository.findAllByMessageId(message.getId());
        if (existsUserMsg.size()>0){
            existsUserMsg.forEach(x->user.remove(x.getUser()));
        }
        List<UserMessage> userMessages = Lists.newArrayList();
        for (User u : user) {
            UserMessage userMessage = new UserMessage();
            userMessage.setUser(u);
            userMessage.setMessage(message);
            userMessage.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
            userMessages.add(userMessage);
        }
        return userMessageRepository.saveAll(userMessages);
    }

    class Spec implements Specification<UserMessage> {

        private transient MessageQueryModel messageQueryModel;

        private Long userId;

        public Spec(MessageQueryModel messageQueryModel, Long userId) {
            this.messageQueryModel = messageQueryModel;
            this.userId = userId;
        }

        @Override
        public Predicate toPredicate(Root<UserMessage> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
            List<Predicate> list = new ArrayList<>();
            Join<UserMessage, User> userJoin = root.join("user", JoinType.LEFT);
            list.add(cb.equal(userJoin.get("id"), userId));
            list.add(cb.equal(root.get("isDelete"), false));

            //已读未读状态
            if(messageQueryModel.getState() != -1){
                list.add(cb.equal(root.get("status"), messageQueryModel.getState()));
            }
            Join<UserMessage, Message> messageJoin = root.join("message", JoinType.LEFT);
            try {
                //删除标识
                list.add(cb.equal(messageJoin.get("deleteFlag"), false));

                //上架状态
                list.add(cb.equal(messageJoin.get("status"), MessageStatus.PUBLISH.getCode()));

                messageService.addValidateTime(messageJoin, cb, list, messageQueryModel);
            }catch (Exception e){
                log.error("获取通知消息接口时间转换失败 startTime={} endTime={}", messageQueryModel.getStartTime(), messageQueryModel.getEndTime(), e);
            }

            //按上架时间倒叙
            criteriaQuery.orderBy(cb.desc(messageJoin.get("onTime")));

            criteriaQuery.distinct(true);

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }
}
