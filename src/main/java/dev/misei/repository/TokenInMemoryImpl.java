package dev.misei.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class TokenInMemoryImpl implements TokenRepository {

    //Token, Email
    private final Map<String, String> inMemoryData = new HashMap<>();

    @Override
    public boolean saveOverrideGeneratedToken(String email, String token) {
        inMemoryData.put(token, email);
        return true;
    }

    @Override
    public Optional<String> retrieveGeneratedToken(String email) {
        return Optional.ofNullable(inMemoryData.get(email));
    }
}
