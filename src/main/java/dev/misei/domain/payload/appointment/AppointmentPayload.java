package dev.misei.domain.payload.appointment;

import dev.misei.domain.payload.business.SimpleBusinessPayload;
import dev.misei.domain.payload.user.SimpleUserPayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentPayload {
    private Long id;

    private String slotOwner;
    private LocalDateTime slotStartAppointment;
    private Long slotDuration;
    private String slotService;

    private SimpleUserPayload user;
    private SimpleBusinessPayload business;
}
