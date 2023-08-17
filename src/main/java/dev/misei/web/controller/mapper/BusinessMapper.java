package dev.misei.web.controller.mapper;

import dev.misei.domain.business.Business;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BusinessMapper {

    BusinessMapper INSTANCE = Mappers.getMapper(BusinessMapper.class);

    @Mapping(target = "appointments", ignore = true)
    Business toEntity(BusinessPayload payload);

    BusinessPayload toPayload(Business entity);
}
