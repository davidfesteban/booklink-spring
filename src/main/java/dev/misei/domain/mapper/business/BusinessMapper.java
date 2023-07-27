package dev.misei.domain.mapper.business;

import dev.misei.domain.entity.Appointment;
import dev.misei.domain.entity.Business;
import dev.misei.domain.payload.appointment.AppointmentPayload;
import dev.misei.domain.payload.business.BusinessPayload;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BusinessMapper {

    BusinessMapper INSTANCE = Mappers.getMapper(BusinessMapper.class);

    Business toEntity(BusinessPayload payload);

    BusinessPayload toPayload(Business entity);

    default BusinessPayload toPayloadNoSensitive(Business entity) {
        var result = toPayload(entity);
        result.setAdmin(null);
        return result;
    }
}
