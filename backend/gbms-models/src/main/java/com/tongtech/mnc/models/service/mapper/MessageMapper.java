package com.tongtech.mnc.models.service.mapper;

import com.tongtech.mnc.mapper.EntityMapper;
import com.tongtech.mnc.models.domain.Message;
import com.tongtech.mnc.models.service.dto.MessageDTO;
import com.tongtech.mnc.models.vo.MessageVo;
import com.tongtech.mnc.models.vo.MessageWithContentVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper extends EntityMapper<MessageDTO, Message> {
    MessageVo toVo(Message message);

    MessageWithContentVo toMessageWithContentVo(Message message);
}
