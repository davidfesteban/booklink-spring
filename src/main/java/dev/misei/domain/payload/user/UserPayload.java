package dev.misei.domain.payload.user;

import dev.misei.domain.payload.appointment.SimpleAppointmentPayload;
import dev.misei.domain.payload.business.SimpleBusinessPayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPayload {
    private Long id;

    private String name;
    private String email;
    private String password;
    private String phone;

    private SimpleBusinessPayload business;
    private Set<SimpleAppointmentPayload> appointments;
}
