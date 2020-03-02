package com.tongtech.mnc.models.service;

import com.tongtech.mnc.models.domain.Message;
import com.tongtech.mnc.models.model.MessageQueryModel;
import com.tongtech.mnc.models.model.MgtMsgQueryModel;
import com.tongtech.mnc.models.service.dto.MessageDTO;
import com.tongtech.mnc.models.service.dto.MessageListDTO;
import com.tongtech.mnc.models.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.List;

public interface MessageService {
    List<Message> findAll(MessageQueryModel messageQueryModel, Timestamp maxOnTime);


    Result findAll(MgtMsgQueryModel model, Pageable pageable);

    Result findById(Long id);

    Result addNew(MessageDTO message);

    Result modifyOne(MessageDTO message,Long id);

    Result dropMessage(Long id);

    Result publishMessage(Long id);

    Result withdrawMessage(Long id);

    void addValidateTime(From root, CriteriaBuilder cb, List<Predicate> list, MessageQueryModel messageQueryModel);
}
