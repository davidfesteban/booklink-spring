package dev.misei.domain.mapper.appointment;

import dev.misei.domain.entity.Appointment;
import dev.misei.domain.payload.appointment.AppointmentPayload;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AppointmentMapper {
    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    Appointment toEntity(AppointmentPayload payload);

    AppointmentPayload toPayload(Appointment entity);
}
