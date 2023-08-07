package dev.misei.application;

import dev.misei.domain.BooklinkException;
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

    public UserPayload findDetailsByAppointmentId(String id, User user) {
        var userByAppointment = authRepository.findByAppointments_Id(id);
        var business = businessRepository.findByAppointments_IdIgnoreCase(id);

        //If user is admin from the business
        if(user.getBusiness().getSubdomain().equalsIgnoreCase(business.orElseThrow(BooklinkException.Type.ID_MISMATCH::boom).getSubdomain())) {
            return UserMapper.INSTANCE.toPayload(userByAppointment.orElseThrow(BooklinkException.Type.USER_NOT_FOUND::boom));
        }

        throw BooklinkException.Type.APPOINTMENT_ID_NOT_FOUND.boom();
    }
}
