package dev.misei.domain.payload.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleUserPayload {
    private String name;
    private String email;
    //TODO: Remove??
    private String password;
    private String phone;
}
