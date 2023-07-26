package dev.misei.domain.mapper.user;

import dev.misei.domain.entity.User;
import dev.misei.domain.payload.user.UserPayload;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserPayload payload);

    UserPayload toPayload(User entity);
}
