package dev.misei.domain.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentPayload {
    //Ignored on in
    private String id;
    private String slotOwner;
    private LocalDateTime slotStartAppointment;

    //Minutes
    private Long slotDuration;
    private String slotService;

    private String manualBookingInfoByAdmin;
    private String manualBookingInviteToJoin;

    //Manually mapped
    private BusinessPayload businessPayload;
    private UserPayload userPayload;
}
