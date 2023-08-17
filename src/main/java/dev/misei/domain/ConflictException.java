package dev.misei.domain;

public class ConflictException extends DomainException {
    public ConflictException() {
    }

    public ConflictException(String message) {
        super(message);
    }
}
