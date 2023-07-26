package dev.misei.domain.mapper.business;

import dev.misei.domain.entity.Business;
import dev.misei.domain.payload.business.SimpleBusinessPayload;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimpleBusinessMapper {

    SimpleBusinessMapper INSTANCE = Mappers.getMapper(SimpleBusinessMapper.class);

    Business toEntity(SimpleBusinessPayload payload);

    SimpleBusinessPayload toPayload(Business entity);
}
