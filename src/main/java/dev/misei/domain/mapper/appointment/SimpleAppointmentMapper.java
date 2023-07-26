package dev.misei.domain.mapper.appointment;

import dev.misei.domain.entity.Appointment;
import dev.misei.domain.payload.appointment.SimpleAppointmentPayload;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimpleAppointmentMapper {
    SimpleAppointmentMapper INSTANCE = Mappers.getMapper(SimpleAppointmentMapper.class);

    Appointment toEntity(SimpleAppointmentPayload payload);

    SimpleAppointmentPayload toPayload(Appointment entity);
}
