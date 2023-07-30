package dev.misei.domain.entity;

import dev.misei.domain.WorkingHours;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Business {
    @Id
    private String subdomain;
    private String name;
    private String address;
    private String email;
    private String phone;

    private Map<String, Long> slotDurationByServices;
    private Map<DayOfWeek, WorkingHours> workingHoursByDay;
    private Set<String> slotOwners;
    private Map<LocalDate, WorkingHours> specialWorkingDays;

    @DocumentReference
    private Set<Appointment> appointments;

    public void addAppointment(Appointment appointment) {
        if (appointments == null) {
            appointments = new HashSet<>();
        }

        appointments.add(appointment);
    }

    public void removeAppointment(Appointment appointment) {
        if (appointments != null && !appointments.isEmpty()) {
            appointments.remove(appointment);
        }
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Business that) && this.subdomain.equalsIgnoreCase(that.subdomain);
    }
}
