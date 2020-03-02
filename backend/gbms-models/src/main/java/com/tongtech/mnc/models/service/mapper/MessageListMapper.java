package com.tongtech.mnc.models.service.mapper;

import com.tongtech.mnc.models.domain.Message;
import com.tongtech.mnc.models.service.dto.MessageListDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageListMapper extends EntityMapper<MessageListDTO, Message> {
}
