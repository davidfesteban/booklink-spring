package dev.misei.application;

import dev.misei.domain.BooklinkException;
import dev.misei.domain.entity.User;
import dev.misei.domain.mapper.AppointmentMapper;
import dev.misei.domain.mapper.BusinessMapper;
import dev.misei.domain.mapper.UserMapper;
import dev.misei.domain.payload.AppointmentPayload;
import dev.misei.domain.payload.BusinessPayload;
import dev.misei.repository.AppointmentRepository;
import dev.misei.repository.AuthRepository;
import dev.misei.repository.BusinessRepository;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BusinessProcessor extends BaseProcessor {

    public BusinessProcessor(BusinessRepository businessRepository, AppointmentRepository appointmentRepository, AuthRepository authRepository) {
        super(businessRepository, appointmentRepository, authRepository);
    }

    public BusinessPayload findBusiness(String subdomain) {
        var payload = BusinessMapper.INSTANCE.toPayload(businessRepository.findBySubdomainIgnoreCase(subdomain).orElseThrow(BooklinkException.Type.UNKNOWN_SUBDOMAIN::boom));

        payload.setAppointments(payload.getAppointments().stream().map(new Function<AppointmentPayload, AppointmentPayload>() {
            @Override
            public AppointmentPayload apply(AppointmentPayload appointmentPayload) {
                return AppointmentMapper.INSTANCE.enrichWith(UserMapper.INSTANCE.toPayload(authRepository.findByAppointments_IdIgnoreCase(
                        appointmentPayload.getId()).orElseThrow(BooklinkException.Type.APPOINTMENT_ID_NOT_FOUND::boom)), appointmentPayload);
            }
        }).collect(Collectors.toSet()));

        return payload;

    }

    public BusinessPayload createBusiness(BusinessPayload businessPayload, User user) {
        var business = BusinessMapper.INSTANCE.toEntity(businessPayload);

        if (user.getBusiness() != null) {
            throw BooklinkException.Type.MORE_THAN_ONE_BUSINESS.boom();
        } else if (businessRepository.existsBySubdomainIgnoreCase(businessPayload.getSubdomain())) {
            throw BooklinkException.Type.ALREADY_TAKEN_SUBDOMAIN.boom();
        }

        user.setBusiness(business);
        authRepository.save(user);

        return BusinessMapper.INSTANCE.toPayload(businessRepository.save(business));
    }

    public BusinessPayload modifyBusinessDetails(BusinessPayload businessPayload, User user) {
        var currentBusiness = BusinessMapper.INSTANCE.toEntity(businessPayload);
        var oldBusiness = user.getBusiness();

        if (!businessRepository.existsBySubdomainIgnoreCase(businessPayload.getSubdomain())) {
            throw BooklinkException.Type.UNKNOWN_SUBDOMAIN.boom();
        } else if (!user.getBusiness().getSubdomain().equalsIgnoreCase(businessPayload.getSubdomain())) {
            throw BooklinkException.Type.USER_NOT_ADMIN.boom();
        }

        currentBusiness.setAppointments(oldBusiness.getAppointments());
        user.setBusiness(currentBusiness);
        authRepository.save(user);

        return BusinessMapper.INSTANCE.toPayload(businessRepository.save(currentBusiness));
    }
}
