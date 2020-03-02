package com.tongtech.mnc.models.service.impl;

import cn.hutool.core.date.DateTime;
import com.google.common.collect.Lists;
import com.tongtech.mnc.enums.MessageStatus;
import com.tongtech.mnc.enums.PublishStatusEnum;
import com.tongtech.mnc.enums.RecevingRangeEnum;
import com.tongtech.mnc.models.domain.Message;
import com.tongtech.mnc.models.domain.User;
import com.tongtech.mnc.models.domain.UserMessage;
import com.tongtech.mnc.models.model.MessageQueryModel;
import com.tongtech.mnc.models.model.MgtMsgQueryModel;
import com.tongtech.mnc.models.repository.MessageRepository;
import com.tongtech.mnc.models.service.MessageService;
import com.tongtech.mnc.models.service.UserMessageService;
import com.tongtech.mnc.models.service.UserService;
import com.tongtech.mnc.models.service.dto.MessageDTO;
import com.tongtech.mnc.models.service.dto.MessageListDTO;
import com.tongtech.mnc.models.service.mapper.MessageListMapper;
import com.tongtech.mnc.models.service.mapper.MessageMapper;
import com.tongtech.mnc.models.vo.Result;
import com.tongtech.mnc.utils.ApplyContants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserMessageService userMessageService;
    @Autowired
    MessageListMapper messageListMapper;
    @Autowired
    MessageMapper messageMapper;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<Message> findAll(MessageQueryModel messageQueryModel, Timestamp maxOnTime) {
        return messageRepository.findAll(new Spec(messageQueryModel, maxOnTime));
    }

    @Override
    public Result findAll(MgtMsgQueryModel model, Pageable pageable) {
        LocalDateTime now = LocalDateTime.now();
        Page<Message> page = messageRepository.findAll(new MgtSpec(model), pageable);
        return Result.success(page.map(p -> {
            MessageListDTO dto = messageListMapper.toDto(p);
            if (now.isBefore(p.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())) {
                dto.setPublishStatus(PublishStatusEnum.WAITING_PUBLISH.getCode());
            } else if (now.isBefore(p.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                    && now.isAfter(p.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())) {
                dto.setPublishStatus(PublishStatusEnum.PUBLISHING.getCode());
            } else {
                dto.setPublishStatus(PublishStatusEnum.EXPIRED.getCode());
            }
            return dto;
        }), "消息列表获取成功！");
    }

    @Override
    public Result findById(Long id) {
        Optional<Message> optional = messageRepository.findByIdAndDeleteFlag(id, false);
        if (optional.isPresent()) {
            Message message = optional.get();
            return Result.success(messageMapper.toDto(message), "消息获取成功！");
        }
        return Result.notFound(null, "查找的消息不存在！");
    }

    @Override
    public Result addNew(MessageDTO dto) {
        Message message = messageMapper.toEntity(dto);
        if (message.getReceivingRange() == RecevingRangeEnum.SPEC_USERS.getCode()) {
            if (StringUtils.isNotEmpty(message.getEnterpriseUniqueCodes())) {
                String enterpriseUniqueCodes = Arrays.stream(message.getEnterpriseUniqueCodes().split(","))
                        .distinct().filter(x -> x.length() > 0)
                        .collect(Collectors.joining(","));
                message.setEnterpriseUniqueCodes(enterpriseUniqueCodes);
            }
            if (StringUtils.isNotEmpty(message.getPersonalUniqueCodes())) {
                String personalUniqueCodes = Arrays.stream(message.getPersonalUniqueCodes().split(","))
                        .distinct().filter(x -> x.length() > 0)
                        .collect(Collectors.joining(","));
                message.setPersonalUniqueCodes(personalUniqueCodes);
            }
        }
        message.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        Message msgSaveResult = messageRepository.save(message);
        if (msgSaveResult.getId() != null) {
            return Result.success(null, "新增成功！");
        }
        return Result.failed(null, "新增失败！");
    }

    @Override
    public Result modifyOne(MessageDTO dto, Long id) {
        if (!messageRepository.findByIdAndDeleteFlag(id, false).isPresent()) {
            return Result.failed(null, "消息不存在，修改失败！");
        }
        Message message = messageMapper.toEntity(dto);
        message.setId(id);
        message.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        messageRepository.save(message);
        return Result.success(null, "修改成功！");
    }

    @Override
    public Result dropMessage(Long id) {
        int res = messageRepository.updateDeleteFlagById(true, id);
        if (res > 0) {
            return Result.success(null, "删除成功！");
        }
        return Result.failed(null, "删除失败！");
    }

    @Override
    public Result publishMessage(Long id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (!optionalMessage.isPresent()) {
            return Result.notFound(null, "上架的消息不存在！");
        }
        Message message = optionalMessage.get();
        message.setStatus(MessageStatus.PUBLISH.getCode());
        //上过架再下架无需重新生成消息与用户的关联
        boolean pubFlag = message.getOnTime() != null;
        message.setOnTime(Timestamp.valueOf(LocalDateTime.now()));
        message = messageRepository.save(message);
        if (pubFlag) {
            return Result.success(null, "上架成功！");
        }
        if (message.getReceivingRange() == RecevingRangeEnum.ALL_USERS.getCode()) {
            return Result.success(null, "上架成功！");
        }
        String personalUniqueCodes = message.getPersonalUniqueCodes();
        String enterpriseUniqueCodes = message.getEnterpriseUniqueCodes();
        if (StringUtils.isEmpty(personalUniqueCodes) && StringUtils.isEmpty(enterpriseUniqueCodes)) {
            return Result.success(null, "上架成功！");
        }
        List<User> users = userService.save(personalUniqueCodes, enterpriseUniqueCodes);
        List<UserMessage> userMessages = userMessageService.saveUserMessages(users, message);
        if (userMessages.size() > 0) {
            return Result.success(null, "上架成功！");
        }
        return Result.failed(null, "上架失败！");
    }

    @Override
    public Result withdrawMessage(Long id) {
        int res = messageRepository.updateStatusById(MessageStatus.WITHDRAW.getCode(), id);
        if (res > 0) {
            return Result.success(null, "下架成功！");
        }
        return Result.failed(null, "下架失败！");
    }

    class Spec implements Specification<Message> {

        private transient MessageQueryModel messageQueryModel;

        private Timestamp maxOnTime;

        public Spec(MessageQueryModel messageQueryModel, Timestamp maxOnTime) {
            this.messageQueryModel = messageQueryModel;
            this.maxOnTime = maxOnTime;
        }

        @Override
        public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
            List<Predicate> list = new ArrayList<>();
            //最大上架时间过滤
            if (maxOnTime != null) {
                list.add(cb.greaterThan(root.get("onTime"), maxOnTime));
            }

            //删除标识
            list.add(cb.equal(root.get("deleteFlag"), false));

            //上架状态
            list.add(cb.equal(root.get("status"), MessageStatus.PUBLISH.getCode()));

            List<Predicate> userTypeAndClientTypeAndRegions = new ArrayList<>();
            //对象类型
            userTypeAndClientTypeAndRegions.add(cb.like(root.get("userType"), ApplyContants.getLikeStr(messageQueryModel.getUserType())));

            userTypeAndClientTypeAndRegions.add(cb.like(root.get("clientType"), ApplyContants.getLikeStr(messageQueryModel.getClientType())));

            //所有用户
            userTypeAndClientTypeAndRegions.add(cb.equal(root.get("allUserSub"), true));

            //行政区划
            if (StringUtils.isNotEmpty(messageQueryModel.getRegion())) {
                for (String preRegion : messageQueryModel.getRegion().split(",")) {
                    userTypeAndClientTypeAndRegions.add(cb.like(root.get("regions"), ApplyContants.getLikeStr(preRegion)));
                }
            }

            Predicate[] userTypeAndClientTypeAndRegionsArr = new Predicate[userTypeAndClientTypeAndRegions.size()];
            list.add(cb.or(userTypeAndClientTypeAndRegions.toArray(userTypeAndClientTypeAndRegionsArr)));
            addValidateTime(root, cb, list, messageQueryModel);

            criteriaQuery.distinct(true);

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }

    /**
     * 添加有效期时间
     *
     * @param root
     * @param cb
     * @param list
     * @param messageQueryModel
     */
    public void addValidateTime(From root, CriteriaBuilder cb, List<Predicate> list, MessageQueryModel messageQueryModel) {
        try {
            Timestamp startTime;
            //开始时间
            if (StringUtils.isNotEmpty(messageQueryModel.getStartTime())) {
                startTime = new Timestamp(sdf.parse(messageQueryModel.getStartTime().trim() + " 00:00:00").getTime());
            } else {
                startTime = new Timestamp(System.currentTimeMillis());
            }
            list.add(cb.lessThanOrEqualTo(root.get("startTime"), startTime));

            //结束时间
            Timestamp endTime;
            if (StringUtils.isNotEmpty(messageQueryModel.getEndTime())) {
                endTime = new Timestamp(sdf.parse(messageQueryModel.getEndTime().trim() + " 23:59:59").getTime());
                if(endTime.after(DateTime.now())){
                    endTime = new Timestamp(DateTime.now().getTime());
                }
            } else {
                endTime = new Timestamp(System.currentTimeMillis());
            }
            list.add(cb.greaterThanOrEqualTo(root.get("endTime"), endTime));
        } catch (Exception e) {
            log.error("获取通知消息接口时间转换失败 startTime={} endTime={}", messageQueryModel.getStartTime(), messageQueryModel.getEndTime(), e);
        }
    }

    class MgtSpec implements Specification<Message> {

        private transient MgtMsgQueryModel mgtMsgQueryModel;

        public MgtSpec(MgtMsgQueryModel model) {
            this.mgtMsgQueryModel = model;
        }

        @Override
        public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
            List<Predicate> list = Lists.newArrayList();

            list.add(cb.equal(root.get("deleteFlag"), false));

            if (!StringUtils.isEmpty(mgtMsgQueryModel.getTitle())) {
                list.add(cb.like(root.get("title"), ApplyContants.getLikeStr(mgtMsgQueryModel.getTitle())));
            }
            if (mgtMsgQueryModel.getMessageType() >= 0) {
                list.add(cb.equal(root.get("messageType"), mgtMsgQueryModel.getMessageType()));
            }
            if (!StringUtils.isEmpty(mgtMsgQueryModel.getUserType())) {
                list.add(cb.like(root.get("userType"), ApplyContants.getLikeStr(mgtMsgQueryModel.getUserType())));
            }
            if (mgtMsgQueryModel.getRange() >= 0) {
                switch (mgtMsgQueryModel.getRange()) {
                    case 0: // 全部用户
                        list.add(cb.equal(root.get("receivingRange"), RecevingRangeEnum.ALL_USERS.getCode()));
                        break;
                    case 1: // 指定用户
                        list.add(cb.equal(root.get("receivingRange"), RecevingRangeEnum.SPEC_USERS.getCode()));
                        break;
                    case 2: // 行政区划
                        list.add(cb.equal(root.get("receivingRange"), RecevingRangeEnum.ADMIN_AREA.getCode()));
                        break;
                }
            }
            if (mgtMsgQueryModel.getContentType() >= 0) {
                list.add(cb.equal(root.get("contentType"), mgtMsgQueryModel.getContentType()));
            }

            Instant now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
            if (mgtMsgQueryModel.getPublishStatus() >= 0) {
                switch (mgtMsgQueryModel.getPublishStatus()) {
                    case 0: //未发布
                        list.add(cb.greaterThan(root.get("startTime"), Date.from(now)));
                        break;
                    case 1: //发布中
                        list.add(cb.and(cb.lessThanOrEqualTo(root.get("startTime")
                                , Date.from(now)), cb.greaterThanOrEqualTo(root.get("endTime"), Date.from(now))));
                        break;
                    case 2: //已过期
                        list.add(cb.lessThan(root.get("endTime"), Date.from(now)));
                        break;
                }
            }

            if (!StringUtils.isEmpty(mgtMsgQueryModel.getClientType())) {
                list.add(cb.like(root.get("clientType"), ApplyContants.getLikeStr(mgtMsgQueryModel.getClientType())));
            }
            if (!StringUtils.isEmpty(mgtMsgQueryModel.getStatus())) {
                list.add(cb.equal(root.get("status"), mgtMsgQueryModel.getStatus()));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }
}
