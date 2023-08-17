package dev.misei.web.controller.mapper;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.function.Function;

@AllArgsConstructor
public class JoinToUserMapper implements Function<UserPayload, User> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User apply(UserPayload payload) {
        var user = new User();
        user.setEmail(payload.getEmail());
        user.setName(payload.getName());
        user.setPassword(passwordEncoder.encode(payload.getPassword()));
        user.setPhone(payload.getPhone());
        return user;
    }
}
