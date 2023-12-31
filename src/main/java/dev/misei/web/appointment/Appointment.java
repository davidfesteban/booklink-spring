package dev.misei.web.appointment;


import dev.misei.domain.core.TimeInterval;

import java.time.LocalDate;
import java.util.UUID;

public class Appointment {

    private UUID id;
    private String description;
    private TimeInterval timeInterval;
    private LocalDate localDate;
}
