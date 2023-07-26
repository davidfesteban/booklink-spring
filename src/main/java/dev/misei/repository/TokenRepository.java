package dev.misei.repository;

import java.util.Optional;

public interface TokenRepository {
    boolean saveOverrideGeneratedToken(String email, String token);

    Optional<String> retrieveGeneratedToken(String email);
}
