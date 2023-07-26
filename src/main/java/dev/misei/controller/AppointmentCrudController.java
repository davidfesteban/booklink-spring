package dev.misei.controller;

import dev.misei.application.AppointmentProcessor;
import dev.misei.application.BusinessProcessor;
import dev.misei.config.jwt.JwtTokenProvider;
import dev.misei.domain.payload.appointment.AppointmentPayload;
import dev.misei.domain.payload.appointment.SimpleAppointmentPayload;
import dev.misei.domain.payload.business.BusinessPayload;
import dev.misei.domain.payload.business.SimpleBusinessPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/private/appointment")
public class AppointmentCrudController extends BaseCrudController {

    private final AppointmentProcessor appointmentProcessor;

    public AppointmentCrudController(JwtTokenProvider jwtTokenProvider, AppointmentProcessor appointmentProcessor) {
        super(jwtTokenProvider);
        this.appointmentProcessor = appointmentProcessor;
    }

    @PostMapping("/create")
    public ResponseEntity<AppointmentPayload> createAppointment(@RequestHeader("Host") String domain, @RequestBody SimpleAppointmentPayload simpleAppointmentPayload, @RequestHeader("Authorization") String tokenRequest) {
        return perform(userEmail -> appointmentProcessor.createAppointment(processDomain(domain), simpleAppointmentPayload, userEmail), tokenRequest);
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentPayload>> findMyAppointments(@RequestHeader("Authorization") String tokenRequest) {
        return perform(appointmentProcessor::findMyAppointments, tokenRequest);
    }

    @PostMapping("/remove")
    public ResponseEntity<AppointmentPayload> removeAppointment(Long id, @RequestHeader("Authorization") String tokenRequest) {
        return perform(userEmail -> appointmentProcessor.removeAppointment(id, userEmail), tokenRequest);
    }
}
