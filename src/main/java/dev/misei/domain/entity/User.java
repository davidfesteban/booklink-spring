package dev.misei.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.HashSet;
import java.util.Set;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String email;
    private String name;
    private String password;
    private String phone;

    @DocumentReference
    private Business business;

    @DocumentReference
    private Set<Appointment> appointments;

    public void addAppointment(Appointment appointment) {
        if (appointments == null) {
            appointments = new HashSet<>();
        }

        appointments.add(appointment);
    }

    public void removeAppointment(Appointment appointment) {
        if (appointments != null && !appointments.isEmpty()) {
            appointments.remove(appointment);
        }
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof User that) && this.email.equalsIgnoreCase(that.email);
    }

}
