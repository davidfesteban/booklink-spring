package dev.misei.web.controller.booking;

import dev.misei.domain.core.TimeInterval;

import java.time.LocalDate;
import java.util.UUID;

public record CreateAppointmentRequest(UUID slotId, LocalDate date, TimeInterval interval, String description) {
}
