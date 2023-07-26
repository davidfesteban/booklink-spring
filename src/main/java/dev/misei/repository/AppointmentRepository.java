package dev.misei.repository;

import dev.misei.domain.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUser_EmailIgnoreCase(@NonNull String email);


}
