package dev.misei.application;

import dev.misei.repository.AppointmentRepository;
import dev.misei.repository.AuthRepository;
import dev.misei.repository.BusinessRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BaseProcessor {
    protected BusinessRepository businessRepository;
    protected AppointmentRepository appointmentRepository;
    protected AuthRepository authRepository;
}
