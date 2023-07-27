package dev.misei.application;

import dev.misei.domain.entity.User;
import dev.misei.domain.mapper.UserMapper;
import dev.misei.domain.payload.UserPayload;
import dev.misei.repository.AppointmentRepository;
import dev.misei.repository.AuthRepository;
import dev.misei.repository.BusinessRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProcessor extends BaseProcessor {

    public UserProcessor(BusinessRepository businessRepository, AppointmentRepository appointmentRepository, AuthRepository authRepository) {
        super(businessRepository, appointmentRepository, authRepository);
    }

    public UserPayload findDetails(User user) {
        return UserMapper.INSTANCE.toPayload(user);
    }

}
