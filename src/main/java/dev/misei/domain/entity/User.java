package dev.misei.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String phone;

    @OneToOne(targetEntity = Business.class)
    private Business business;

    @OneToMany(targetEntity = Appointment.class, mappedBy = "user")
    private Set<Appointment> appointments = new HashSet<>();

    public void addBusiness(Business business) {
        this.business = business;
        business.setAdmin(this);
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointment.addUser(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
