package dev.misei.domain.booking;

import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends Repository<Booking, UUID> {
    Optional<Booking> GetBookingByBusinessId(UUID businessId);

    void save(Booking booking);
}
