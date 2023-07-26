package dev.misei.domain.mapper.user;

import dev.misei.domain.entity.User;
import dev.misei.domain.payload.user.SimpleUserPayload;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimpleUserMapper {
    SimpleUserMapper INSTANCE = Mappers.getMapper(SimpleUserMapper.class);

    User toEntity(SimpleUserPayload payload);

    SimpleUserPayload toPayload(User entity);
}
