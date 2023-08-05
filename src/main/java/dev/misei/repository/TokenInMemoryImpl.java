package dev.misei.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class TokenInMemoryImpl implements TokenRepository {

    //Email, Token
    private final Map<String, String> inMemoryData = new HashMap<>();

    @Override
    public boolean saveOverrideGeneratedToken(String email, String token) {
        inMemoryData.put(email, token);
        return true;
    }

    @Override
    public Optional<String> retrieveGeneratedToken(String email) {
        return Optional.ofNullable(inMemoryData.get(email));
    }
}
