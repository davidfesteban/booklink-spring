package dev.misei.application;

import dev.misei.domain.ActionResult;
import dev.misei.domain.entity.Appointment;
import dev.misei.domain.entity.WorkingHours;
import dev.misei.domain.mapper.appointment.AppointmentMapper;
import dev.misei.domain.mapper.appointment.SimpleAppointmentMapper;
import dev.misei.domain.payload.appointment.AppointmentPayload;
import dev.misei.domain.payload.appointment.SimpleAppointmentPayload;
import dev.misei.repository.AppointmentRepository;
import dev.misei.repository.AuthRepository;
import dev.misei.repository.BusinessRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentProcessor {

    private final SimpleAppointmentMapper simpleAppointmentMapper;
    private final AppointmentMapper appointmentMapper;

    private BusinessRepository businessRepository;
    private AppointmentRepository appointmentRepository;
    private AuthRepository authRepository;

    //TODO: Too much cases on early return. Appointment mapping should be at the end to avoid modifications in the middle
    @Transactional
    public ActionResult<AppointmentPayload> createAppointment(String domain, SimpleAppointmentPayload simpleAppointmentPayload, String userEmail) {
        var user = authRepository.findByEmail(userEmail);
        var business = businessRepository.findBySubdomainIgnoreCase(domain);

        if (user.isEmpty()) {
            return ActionResult.FALSE("User not found");
        }

        if (business.isEmpty()) {
            return ActionResult.FALSE("Business not found");
        }

        var appointment = simpleAppointmentMapper.toEntity(simpleAppointmentPayload);
        var currentAppointments = business.get().getAppointments();

        if (business.get().getSlotDurationByServices().get(appointment.getSlotService()).longValue() != appointment.getSlotDuration()) {
            return ActionResult.FALSE("The current service doesn´t match on duration");
        }

        if (currentAppointments.stream()
                .anyMatch(that -> that.compareTo(appointment) == 0)) {
            return ActionResult.FALSE("The appointment provided is conflicting with another");
        }

        if (user.get().getAppointments().stream().anyMatch(that -> that.compareTo(appointment) == 0)) {
            return ActionResult.FALSE("You cannot be in two places at the same time");
        }

        var specialWorkingHours = business.get().getSpecialWorkingDays().get(appointment.getSlotStartAppointment().toLocalDate());
        var normalWorkingHours = business.get().getWorkingHoursByDay().get(appointment.getSlotStartAppointment().getDayOfWeek());
        if (specialWorkingHours != null && isAppointmentWithinWorkingHours(appointment, specialWorkingHours) ||
                normalWorkingHours != null && isAppointmentWithinWorkingHours(appointment, normalWorkingHours)) {

            appointment.addBusiness(business.get());
            appointment.addUser(user.get());

            return ActionResult.TRUE(appointmentMapper.toPayload(appointmentRepository.saveAndFlush(appointment)));
        }

        return ActionResult.FALSE("Appointment is not within current business working hours");

    }

    public ActionResult<List<AppointmentPayload>> findMyAppointments(String userEmail) {
        var user = authRepository.findByEmail(userEmail);

        if (user.isEmpty()) {
            return ActionResult.FALSE("User not found");
        }

        var appointments = appointmentRepository.findByUser_EmailIgnoreCase(userEmail);
        return ActionResult.TRUE(appointments.stream().map(appointmentMapper::toPayload).toList());
    }

    public ActionResult<AppointmentPayload> removeAppointment(Long id, String userEmail) {
        return ActionResult.FALSE("Not implemented");
    }

    private boolean isAppointmentWithinWorkingHours(Appointment that, WorkingHours workingHours) {
        return (workingHours.workStartHour().isBefore(that.getSlotStartAppointment().toLocalTime()) &&
                workingHours.breakStartHour().isAfter(that.getSlotStartAppointment().toLocalTime().plus(that.getSlotDuration(), ChronoUnit.MINUTES))) ||
                (workingHours.breakStopHour().isBefore(that.getSlotStartAppointment().toLocalTime()) &&
                        workingHours.workStopHour().isAfter(that.getSlotStartAppointment().toLocalTime().plus(that.getSlotDuration(), ChronoUnit.MINUTES)));
    }
}
