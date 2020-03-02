package com.tongtech.mnc.models.service.mapper;

import com.tongtech.mnc.models.domain.UserMessage;
import com.tongtech.mnc.models.service.dto.UserMessageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMessageMapper extends EntityMapper<UserMessageDTO, UserMessage> {
}
