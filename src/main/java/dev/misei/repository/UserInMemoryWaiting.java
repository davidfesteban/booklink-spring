package dev.misei.repository;

import dev.misei.domain.entity.User;
import dev.misei.domain.payload.UserPayload;

import java.util.UUID;

public interface UserInMemoryWaiting {

    UUID sendConfirmation(UserPayload user);

    UserPayload confirm(UUID uuid);
}
