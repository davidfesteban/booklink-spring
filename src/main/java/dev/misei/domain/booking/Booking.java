package dev.misei.domain.booking;

import dev.misei.domain.appointment.Appointment;
import dev.misei.domain.customer.Customer;
import dev.misei.web.business.TimeInterval;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Booking {

    private UUID businessId;
    private List<Slot> slots;

    public Appointment createAppointment(Customer customer, UUID slotId, LocalDate localDate, TimeInterval timeInterval, String description) {
        var slotFound = slots.stream().filter(slot -> slot.getId().equals(slotId)).findFirst().orElseThrow();
        return slotFound.createAppointment(customer, localDate, timeInterval, description);
    }
}
