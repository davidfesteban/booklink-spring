package dev.misei.controller;

import dev.misei.application.AppointmentProcessor;
import dev.misei.config.jwt.JwtTokenProvider;
import dev.misei.domain.payload.AppointmentPayload;
import dev.misei.repository.AuthRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/private/appointment")
public class AppointmentCrudController extends BaseCrudController {

    private final AppointmentProcessor appointmentProcessor;

    public AppointmentCrudController(JwtTokenProvider jwtTokenProvider, AuthRepository authRepository, AppointmentProcessor appointmentProcessor) {
        super(jwtTokenProvider, authRepository);
        this.appointmentProcessor = appointmentProcessor;
    }

    @PostMapping("/create")
    public ResponseEntity<AppointmentPayload> createAppointment(@RequestHeader("Host") String domain, @RequestBody AppointmentPayload appointmentPayload, @RequestHeader("Authorization") String tokenRequest) {
        return perform(userEmail -> appointmentProcessor.createAppointment(processDomain(domain), appointmentPayload, userEmail), tokenRequest);
    }

    /*@GetMapping("/appointments")
    public ResponseEntity<List<AppointmentPayload>> findMyAppointments(@RequestHeader("Authorization") String tokenRequest) {
        return perform(appointmentProcessor::findMyAppointments, tokenRequest);
    }*/

    /*@PostMapping("/remove")
    public ResponseEntity<AppointmentPayload> removeAppointment(Long id, @RequestHeader("Authorization") String tokenRequest) {
        return perform(userEmail -> appointmentProcessor.removeAppointment(id, userEmail), tokenRequest);
    }*/
}
