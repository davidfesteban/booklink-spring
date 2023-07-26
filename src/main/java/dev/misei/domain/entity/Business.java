package dev.misei.domain.entity;

import dev.misei.domain.converter.DayOfWeekAndWorkingHoursConverter;
import dev.misei.domain.converter.LocalDateAndWorkingHoursConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Business {
    @Id
    private String subdomain;

    private String name;
    private String address;
    private String email;
    private String phone;

    @ElementCollection
    @MapKeyColumn(name = "key_column")
    @Column(name = "value_column")
    private Map<String, Long> slotDurationByServices;

    @ElementCollection
    @MapKeyColumn(name = "key_column")
    @Column(name = "value_column")
    @Convert(converter = DayOfWeekAndWorkingHoursConverter.class)
    private Map<DayOfWeek, WorkingHours> workingHoursByDay;

    private Set<String> slotOwners;

    @ElementCollection
    @MapKeyColumn(name = "key_column")
    @Column(name = "value_column")
    @Convert(converter = LocalDateAndWorkingHoursConverter.class)
    private Map<LocalDate, WorkingHours> specialWorkingDays;

    @OneToOne(targetEntity = User.class, mappedBy = "business", cascade = CascadeType.MERGE)
    private User admin;

    @OneToMany(targetEntity = Appointment.class, mappedBy = "business")
    private Set<Appointment> appointments = new HashSet<>();

    public void addAdmin(User user) {
        this.admin = user;
        user.addBusiness(this);
    }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
        appointment.addBusiness(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Business that = (Business) o;
        return subdomain != null && subdomain.equalsIgnoreCase(that.subdomain);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
