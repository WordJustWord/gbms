package com.tongtech.mnc.controller;

import com.tongtech.mnc.models.controller.BaseController;
import com.tongtech.mnc.models.service.MessageService;
import com.tongtech.mnc.models.service.dto.MessageDTO;
import com.tongtech.mnc.models.service.mapper.MessageMapper;
import com.tongtech.mnc.models.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
public class MsgOperationController extends BaseController {

    @Autowired
    MessageService messageService;

    @Autowired
    MessageMapper mapper;

    @ApiOperation("新增消息")
    @PostMapping("/message")
    public ResponseEntity addNewMessage(@Validated @RequestBody MessageDTO dto) {
        Result result = messageService.addNew(dto);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    @ApiOperation("修改一条消息")
    @PutMapping("/message/{id}")
    public ResponseEntity updateMessage(@PathVariable Long id,@Validated @RequestBody MessageDTO dto) {
        Result result = messageService.modifyOne(dto, id);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    @ApiOperation("删除一条消息")
    @DeleteMapping("/message/{id}")
    public ResponseEntity deleteMessage(@PathVariable Long id) {
        Result result = messageService.dropMessage(id);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    @ApiOperation("上架消息")
    @PutMapping("/publish/{id}")
    public ResponseEntity publishMessage(@PathVariable Long id) {
        Result result = messageService.publishMessage(id);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    @ApiOperation("下架消息")
    @PutMapping("/withdraw/{id}")
    public ResponseEntity withdrawMessage(@PathVariable Long id) {
        Result result = messageService.withdrawMessage(id);
        return new ResponseEntity(result,HttpStatus.OK);
    }

}
