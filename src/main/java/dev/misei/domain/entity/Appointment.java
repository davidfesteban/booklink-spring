package dev.misei.domain.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Document
@Data
@AllArgsConstructor
public class Appointment implements Comparable<Appointment> {

    @Id
    private String id;
    private String slotOwner;
    private LocalDateTime slotStartAppointment;
    //Minutes
    private Long slotDuration;
    private String slotService;

    private String manualBookingInfoByAdmin;
    private String manualBookingInviteToJoin;

    @Override
    public int compareTo(@NotNull Appointment that) {

        if (that.slotStartAppointment.isAfter(this.slotStartAppointment.plus(this.slotDuration, ChronoUnit.MINUTES))) {
            //This appointment is happening before that
            return -1;
        } else if (that.slotStartAppointment.plus(that.slotDuration, ChronoUnit.MINUTES).isBefore(this.slotStartAppointment)) {
            //This appointment is happening after that
            return 1;
        } else if (!that.slotOwner.equalsIgnoreCase(this.slotOwner)) {
            //This appointment is conflicting in time but in different slots
            return 1;
        }

        //There is a conflict. Both appointments are within the range and slots
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Appointment that) && this.id.equals(that.id);
    }
}
