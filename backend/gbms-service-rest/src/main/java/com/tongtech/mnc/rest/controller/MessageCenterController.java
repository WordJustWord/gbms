package com.tongtech.mnc.rest.controller;

import com.tongtech.mnc.models.controller.BaseController;
import com.tongtech.mnc.models.model.MessageEditStatusModel;
import com.tongtech.mnc.models.model.MessageQueryModel;
import com.tongtech.mnc.models.service.UserMessageService;
import com.tongtech.mnc.models.service.dto.UserMessageDTO;
import com.tongtech.mnc.models.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "消息接口", tags = "消息接口")
@RestController
public class MessageCenterController extends BaseController {
    @Autowired
    UserMessageService userMessageService;

    @ApiOperation("获取通知消息列表")
    @GetMapping(value = "/getNoticeMessage")
    public ResponseEntity<Result> getNoticeMessage(@Validated MessageQueryModel messageQueryModel, Pageable pageable){
        return new ResponseEntity(userMessageService.getNoticeMessage(messageQueryModel, pageable), HttpStatus.OK);
    }

    @ApiOperation("获取通知消息详情")
    @GetMapping(value = "/getNoticeMessageDetail")
    public ResponseEntity<Result> getNoticeMessageDetail(@Validated MessageEditStatusModel messageEditStatusModel, Pageable pageable){
        return new ResponseEntity(userMessageService.getNoticeMessageDetail(messageEditStatusModel), HttpStatus.OK);
    }

    @ApiOperation("修改消息状态")
    @PutMapping(value = "/editMessageStatus")
    public ResponseEntity editMessageStatus(@Validated @RequestBody MessageEditStatusModel messageEditStatusModel){
        return new ResponseEntity(userMessageService.editMessageStatus(messageEditStatusModel), HttpStatus.OK);
    }

    @ApiOperation("删除消息")
    @DeleteMapping(value = "/delete")
    public ResponseEntity deleteMessage(@Validated @RequestBody MessageEditStatusModel messageEditStatusModel){
        return new ResponseEntity(userMessageService.deleteMessage(messageEditStatusModel), HttpStatus.OK);
    }
}

