package dev.misei.domain.mapper;

import dev.misei.domain.entity.Appointment;
import dev.misei.domain.payload.AppointmentPayload;
import dev.misei.domain.payload.BusinessPayload;
import dev.misei.domain.payload.UserPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AppointmentMapper {
    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    @Mapping(target = "id", ignore = true)
    Appointment toEntity(AppointmentPayload payload);

    AppointmentPayload toPayload(Appointment entity);

    default AppointmentPayload enrichWith(UserPayload userPayload, AppointmentPayload appointmentPayload) {
        appointmentPayload.setUserPayload(userPayload);
        return appointmentPayload;
    }

    default AppointmentPayload enrichWith(BusinessPayload businessPayload, AppointmentPayload appointmentPayload) {
        appointmentPayload.setBusinessPayload(businessPayload);
        return appointmentPayload;
    }
}
