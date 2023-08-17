package dev.misei.repository;

import java.util.UUID;

public interface UserInMemoryWaiting {

    UUID sendConfirmation(UserPayload user);

    UserPayload confirm(UUID uuid);
}
