package dev.misei.application;

import dev.misei.domain.BooklinkException;
import dev.misei.domain.WorkingHours;
import dev.misei.domain.entity.Appointment;
import dev.misei.domain.entity.User;
import dev.misei.domain.mapper.AppointmentMapper;
import dev.misei.domain.payload.AppointmentPayload;
import dev.misei.repository.AppointmentRepository;
import dev.misei.repository.AuthRepository;
import dev.misei.repository.BusinessRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
public class AppointmentProcessor extends BaseProcessor {

    public AppointmentProcessor(BusinessRepository businessRepository, AppointmentRepository appointmentRepository, AuthRepository authRepository) {
        super(businessRepository, appointmentRepository, authRepository);
    }

    public AppointmentPayload createAppointment(String domain, AppointmentPayload appointmentPayload, User user) {
        var business = businessRepository.findBySubdomainIgnoreCase(domain).orElseThrow(BooklinkException.Type.BUSINESS_NOT_FOUND::boom);
        var currentAppointments = business.getAppointments();
        var appointment = AppointmentMapper.INSTANCE.toEntity(appointmentPayload);

        if (business.getSlotDurationByServices().get(appointment.getSlotService()).longValue() != appointment.getSlotDuration()) {
            throw BooklinkException.Type.SERVICE_NOT_MATCHING_DURATION.boom();
        } else if (currentAppointments.stream().anyMatch(that -> that.compareTo(appointment) == 0)) {
            throw BooklinkException.Type.APPOINTMENT_CONFLICT.boom();
        } else if (!user.getBusiness().getSubdomain().equalsIgnoreCase(domain) && user.getAppointments().stream().anyMatch(that -> that.compareTo(appointment) == 0)) {
            //In this case, only admins can book more than one time, due to phone booking
            throw BooklinkException.Type.APPOINTMENT_CONFLICT_USER.boom();
        } else if (!isAppointmentWithinWorkingHours(appointment, business.getSpecialWorkingDays().get(appointment.getSlotStartAppointment().toLocalDate())) &&
                !isAppointmentWithinWorkingHours(appointment, business.getWorkingHoursByDay().get(appointment.getSlotStartAppointment().getDayOfWeek()))) {
            throw BooklinkException.Type.APPOINTMENT_NOT_SUITABLE.boom();
        }

        business.addAppointment(appointment);
        user.addAppointment(appointment);

        businessRepository.save(business);
        authRepository.save(user);

        return AppointmentMapper.INSTANCE.toPayload(appointmentRepository.save(appointment));
    }

    public AppointmentPayload removeAppointment(String id, User user) {
        var business = businessRepository.findByAppointments_IdIgnoreCase(id).orElseThrow(BooklinkException.Type.BUSINESS_NOT_FOUND::boom);
        var appointment = appointmentRepository.findByIdIgnoreCase(id).orElseThrow(BooklinkException.Type.APPOINTMENT_ID_NOT_FOUND::boom);
        var userRegistered = authRepository.findByAppointments_IdIgnoreCase(id).orElseThrow(BooklinkException.Type.USER_FOR_APPOINTMENT_NOT_FOUND::boom);

        if (!userRegistered.getEmail().equalsIgnoreCase(user.getEmail()) || user.getBusiness() == null || !user.getBusiness().getSubdomain().equalsIgnoreCase(business.getSubdomain())) {
            throw BooklinkException.Type.USER_NOT_ADMIN.boom();
        }

        business.removeAppointment(appointment);
        userRegistered.removeAppointment(appointment);

        appointmentRepository.delete(appointment);
        businessRepository.save(business);
        authRepository.save(userRegistered);

        return AppointmentMapper.INSTANCE.toPayload(appointment);
    }


    private boolean isAppointmentWithinWorkingHours(Appointment that, WorkingHours workingHours) {
        return (workingHours.workStartHour().isBefore(that.getSlotStartAppointment().toLocalTime()) &&
                workingHours.breakStartHour().isAfter(that.getSlotStartAppointment().toLocalTime().plus(that.getSlotDuration(), ChronoUnit.MINUTES))) ||
                (workingHours.breakStopHour().isBefore(that.getSlotStartAppointment().toLocalTime()) &&
                        workingHours.workStopHour().isAfter(that.getSlotStartAppointment().toLocalTime().plus(that.getSlotDuration(), ChronoUnit.MINUTES)));
    }


}
