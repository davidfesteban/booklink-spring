package dev.misei.application;


import dev.misei.domain.appointment.Appointment;
import dev.misei.domain.booking.Booking;
import dev.misei.domain.booking.BookingRepository;
import dev.misei.domain.core.TimeInterval;
import dev.misei.domain.customer.Customer;
import dev.misei.domain.customer.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;


    public Appointment createAppointment( String userAuthId, UUID businessId, UUID slotId, LocalDate date, TimeInterval interval, String description) {
        Customer customer = customerRepository.getByAuthId(userAuthId).orElseThrow();
        Booking booking = bookingRepository.GetBookingByBusinessId(businessId).orElseThrow();
        Appointment appointment = booking.createAppointment(slotId, date, interval, description, customer.getId());
        bookingRepository.save(booking);
        return appointment;
    }
}
