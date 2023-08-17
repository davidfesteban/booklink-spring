package dev.misei.domain.booking;

import dev.misei.domain.appointment.Appointment;
import dev.misei.domain.business.DeviatingHours;
import dev.misei.domain.business.OpeningHours;
import dev.misei.domain.customer.Customer;
import dev.misei.web.business.TimeInterval;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class Slot {

    private UUID id;
    private String name;
    private OpeningHours openingHours;

    private DeviatingHours deviatingHours;

    private List<Appointment> appointments;

    public Appointment createAppointment(Customer customer, LocalDate localDate, TimeInterval timeInterval, String description) {
        var appointment = new Appointment();
        //Validation

        return appointment;
    }
}
