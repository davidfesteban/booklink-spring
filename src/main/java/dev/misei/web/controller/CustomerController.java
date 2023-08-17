package dev.misei.web.controller;

import dev.misei.domain.appointment.Appointment;
import dev.misei.domain.business.Business;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/private/customer")
public class CustomerController {

    @PostMapping("/appointments")
    public ResponseEntity<Appointment> createBusiness(@RequestBody AppointmentRequest businessRequest, @RequestHeader("Authorization") String tokenRequest) {
        return perform(user -> businessProcessor.createBusiness(businessPayload, user), tokenRequest);
    }

}
