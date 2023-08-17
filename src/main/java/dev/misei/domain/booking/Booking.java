package dev.misei.domain.booking;

import dev.misei.domain.appointment.Appointment;
import dev.misei.domain.core.TimeInterval;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Booking extends AbstractAggregateRoot<Booking> {

    private UUID id;
    private UUID businessId;
    private List<Slot> slots;
    @Version
    private Integer version;

    public Appointment createAppointment(UUID slotId, LocalDate localDate, TimeInterval timeInterval, String description, UUID customerId) {
        var slotFound = slots.stream().filter(slot -> slot.getId().equals(slotId)).findFirst().orElseThrow();
        var appointment = slotFound.createAppointment(customerId, localDate, timeInterval, description);
        registerEvent(new Events.AppointmentCreated(appointment.getId(), appointment.getCustomerId()));
        return appointment;
    }

    static class Events {
        record AppointmentCreated(UUID id, UUID customerId){}
    }
}
