package dev.misei.domain.payload.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleAppointmentPayload {
    private Long id;

    private String slotOwner;
    private LocalDateTime slotStartAppointment;
    private Long slotDuration;
    private String slotService;
}
