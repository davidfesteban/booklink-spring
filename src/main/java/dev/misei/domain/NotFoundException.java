package dev.misei.domain;

import java.util.UUID;

public class NotFoundException extends DomainException {
    public NotFoundException(UUID id) {
        super("Not found: " + id);
    }
}
