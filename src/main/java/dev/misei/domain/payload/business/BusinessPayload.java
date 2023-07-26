package dev.misei.domain.payload.business;

import dev.misei.domain.entity.WorkingHours;
import dev.misei.domain.payload.appointment.SimpleAppointmentPayload;
import dev.misei.domain.payload.user.SimpleUserPayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessPayload {
    private String subdomain;
    private String name;
    private String address;
    private String email;
    private String phone;

    private Map<String, Long> slotDurationByServices;
    private Map<DayOfWeek, WorkingHours> workingHoursByDay;
    private Set<String> slotOwners;
    private Map<LocalDate, WorkingHours> specialWorkingDays;

    private SimpleUserPayload admin;

    private Set<SimpleAppointmentPayload> appointments;
}
