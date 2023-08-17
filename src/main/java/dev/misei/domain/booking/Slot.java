package dev.misei.domain.booking;

import dev.misei.domain.ConflictException;
import dev.misei.domain.appointment.Appointment;
import dev.misei.domain.business.DeviatingHours;
import dev.misei.domain.business.OpeningHours;
import dev.misei.domain.business.OpeningHoursByDay;
import dev.misei.domain.core.Entity;
import dev.misei.domain.core.TimeInterval;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
public class Slot extends Entity {

    private UUID bookingId;
    private String name;
    private OpeningHours openingHours;
    private DeviatingHours deviatingHours;
    private List<Appointment> appointments;

    public Appointment createAppointment(UUID customerId, LocalDate localDate, TimeInterval timeInterval, String description) {

        if (isConflictingWithOtherAppointment(localDate, timeInterval)) {
            throw new ConflictException("There is a conflicting appointment");
        }

        if (!fitsIntoInterval(localDate, timeInterval)) {
            throw new ConflictException("No slot available for this time");
        }

        Appointment appointment = new Appointment(customerId, description, timeInterval, localDate);
        appointments.add(appointment);
        return appointment;
    }

    private boolean isConflictingWithOtherAppointment(LocalDate localDate, TimeInterval timeInterval) {
        return appointments.stream().filter(it -> it.getLocalDate().equals(localDate))
                .anyMatch(it -> it.getTimeInterval().isConflictingWith(timeInterval));
    }

    private boolean fitsIntoInterval(LocalDate localDate, TimeInterval timeInterval) {
        var openingHoursByDay = getOpeningHoursByDay(localDate);
        return openingHoursByDay.isAvailableForInterval(timeInterval);
    }

    private OpeningHoursByDay getOpeningHoursByDay(LocalDate date) {
        // first check if there are deviating hours for this date
        var deviatingHoursOpeningHours = deviatingHours.getOpeningHours(date);
        if (deviatingHoursOpeningHours.isPresent()) {
            return deviatingHoursOpeningHours.get().openingHoursByDay();
        } else {
            var day = date.getDayOfWeek().getValue() - 1;
            return openingHours.getAll().get(day);
        }
    }
}
