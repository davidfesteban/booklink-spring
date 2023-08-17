package dev.misei.domain.customer;

import dev.misei.domain.appointment.Appointment;
import dev.misei.domain.core.Entity;
import dev.misei.domain.user.User;
import lombok.Getter;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
public class Customer extends Entity {
    private User user;
    private List<Appointment> appointments;
}

