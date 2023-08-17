package dev.misei.web.controller.mapper;

import dev.misei.web.appointment.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AppoinmentMapper {
    AppoinmentMapper INSTANCE = Mappers.getMapper(AppoinmentMapper.class);

    Appointment toWeb(dev.misei.domain.appointment.Appointment entity);
}
