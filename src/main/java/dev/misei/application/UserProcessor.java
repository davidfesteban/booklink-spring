package dev.misei.application;

import dev.misei.domain.BooklinkException;
import dev.misei.domain.entity.User;
import dev.misei.domain.mapper.AppointmentMapper;
import dev.misei.domain.mapper.BusinessMapper;
import dev.misei.domain.mapper.UserMapper;
import dev.misei.domain.payload.AppointmentPayload;
import dev.misei.domain.payload.UserPayload;
import dev.misei.repository.AppointmentRepository;
import dev.misei.repository.AuthRepository;
import dev.misei.repository.BusinessRepository;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserProcessor extends BaseProcessor {

    public UserProcessor(BusinessRepository businessRepository, AppointmentRepository appointmentRepository, AuthRepository authRepository) {
        super(businessRepository, appointmentRepository, authRepository);
    }

    public UserPayload findDetails(User user) {
        var payload = UserMapper.INSTANCE.toPayload(user);

        payload.setAppointments(payload.getAppointments().stream().map(new Function<AppointmentPayload, AppointmentPayload>() {
            @Override
            public AppointmentPayload apply(AppointmentPayload appointmentPayload) {
                return AppointmentMapper.INSTANCE.enrichWith(BusinessMapper.INSTANCE.toPayload(businessRepository.findByAppointments_IdIgnoreCase(
                        appointmentPayload.getId()).orElseThrow(BooklinkException.Type.APPOINTMENT_ID_NOT_FOUND::boom)), appointmentPayload);
            }
        }).collect(Collectors.toSet()));

        return payload;
    }
}
