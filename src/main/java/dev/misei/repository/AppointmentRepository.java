package dev.misei.repository;

import dev.misei.domain.entity.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {
    Optional<Appointment> findByIdIgnoreCase(String s);
    //List<Appointment> findByUser_EmailIgnoreCase(@NonNull String email);
}
