package com.tongtech.mnc.controller;

import com.tongtech.mnc.models.controller.BaseController;
import com.tongtech.mnc.models.model.MgtMsgQueryModel;
import com.tongtech.mnc.models.service.MessageService;
import com.tongtech.mnc.models.service.dto.MessageDTO;
import com.tongtech.mnc.models.service.dto.MessageListDTO;
import com.tongtech.mnc.models.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MsgQueryController extends BaseController {

    @Autowired
    MessageService messageService;

    @PostMapping("/getMessageList")
    public ResponseEntity getMessageList(@RequestBody MgtMsgQueryModel model, Pageable pageable) {
        return new ResponseEntity(messageService.findAll(model,pageable), HttpStatus.OK);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity getOneMsgDetail(@PathVariable Long id) {
        return new ResponseEntity(messageService.findById(id),HttpStatus.OK);
    }
}
