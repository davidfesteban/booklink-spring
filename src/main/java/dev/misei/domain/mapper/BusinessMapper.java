package dev.misei.domain.mapper;

import dev.misei.domain.entity.Business;
import dev.misei.domain.payload.BusinessPayload;
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
