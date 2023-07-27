package dev.misei.domain.mapper;

import dev.misei.domain.entity.Appointment;
import dev.misei.domain.payload.AppointmentPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AppointmentMapper {
    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    @Mapping(target = "id", ignore = true)
    Appointment toEntity(AppointmentPayload payload);

    AppointmentPayload toPayload(Appointment entity);
}
