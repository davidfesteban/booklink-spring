package dev.misei.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Appointment implements Comparable<Appointment> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String slotOwner;
    private LocalDateTime slotStartAppointment;
    //Minutes
    private Long slotDuration;
    private String slotService;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToOne(targetEntity = Business.class)
    private Business business;

    public void addBusiness(Business business) {
        this.business = business;
        business.addAppointment(this);
    }

    public void addUser(User user) {
        this.user = user;
        user.addAppointment(this);
    }

    @Override
    public int compareTo(@NotNull Appointment that) {

        if (that.getSlotStartAppointment().isAfter(this.slotStartAppointment.plus(this.slotDuration, ChronoUnit.MINUTES))) {
            //This appointment is happening before that
            return -1;
        } else if (that.getSlotStartAppointment().plus(that.getSlotDuration(), ChronoUnit.MINUTES).isBefore(this.slotStartAppointment)) {
            //This appointment is happening after that
            return 1;
        } else if(!that.getSlotOwner().equalsIgnoreCase(this.slotOwner)) {
            //This appointment is conflicting in time but in different slots
            return 1;
        }

        //There is a conflict. Both appointments are within the range and slots
        return 0;
    }
}
