package dev.misei.web.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "appointments", ignore = true)
    @Mapping(target = "business", ignore = true)
    User toEntity(UserPayload payload);

    @Mapping(target = "password", ignore = true)
    UserPayload toPayload(User entity);
}
