package dev.misei.domain.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPayload {
    private String name;
    private String email;
    //Ignored on out
    private String password;
    private String phone;

    //Ignored on in
    private BusinessPayload business;
    private Set<AppointmentPayload> appointments;
}
