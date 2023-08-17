package dev.misei.web.controller.booking;

import dev.misei.application.BookingService;
import dev.misei.web.appointment.Appointment;
import dev.misei.web.controller.mapper.AppoinmentMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/business/{businessId}/booking")
@AllArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final AppoinmentMapper appoinmentMapper;

    @PostMapping("/appointments")
    public Appointment createAppointment(Principal principal, @RequestBody CreateAppointmentRequest request, @PathVariable("businessId") UUID businessId) {
        var appointment = bookingService.createAppointment(
                principal.getName(),
                businessId,
                request.slotId(),
                request.date(),
                request.interval(),
                request.description()
        );
        return appoinmentMapper.toWeb(appointment);
    }

}
