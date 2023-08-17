package dev.misei.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {
    Optional<Appointment> findByIdIgnoreCase(String s);
    //List<Appointment> findByUser_EmailIgnoreCase(@NonNull String email);
}
