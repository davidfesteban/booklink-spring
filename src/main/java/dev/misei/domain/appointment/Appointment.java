package dev.misei.domain.appointment;

import dev.misei.domain.core.TimeInterval;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class Appointment {

    @Id
    private final UUID id;
    private final UUID customerId;
    private final String description;
    private final TimeInterval timeInterval;
    private final LocalDate localDate;

    public Appointment(UUID customerId, String description, TimeInterval timeInterval, LocalDate localDate) {
        this.customerId = customerId;
        this.description = description;
        this.timeInterval = timeInterval;
        this.localDate = localDate;
        this.id = UUID.randomUUID();
    }
}
