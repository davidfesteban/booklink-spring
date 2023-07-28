package dev.misei.repository;

import dev.misei.domain.entity.User;
import dev.misei.domain.payload.UserPayload;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class UserInMemoryWaitingImpl implements UserInMemoryWaiting{

    //UUID, User
    private final Map<UUID, UserPayload> inMemoryData = new HashMap<>();

    @Override
    public UUID sendConfirmation(UserPayload user) {
        AtomicReference<UUID> key = new AtomicReference<>(UUID.randomUUID());

        inMemoryData.entrySet().stream().filter(uuidUserEntry -> uuidUserEntry.getValue().equals(user))
                .findFirst().ifPresent(uuidUserEntry -> key.set(uuidUserEntry.getKey()));

        inMemoryData.put(key.get(), user);

        return key.get();
    }

    @Override
    public UserPayload confirm(UUID uuid) {
        var user = inMemoryData.get(uuid);
        inMemoryData.remove(uuid, user);
        return user;
    }
}
