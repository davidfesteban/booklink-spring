package dev.misei.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends DomainException {
    public NotFoundException(UUID id) {
        super("Not found: " + id);
    }
}
