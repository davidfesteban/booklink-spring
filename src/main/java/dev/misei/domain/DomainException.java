package dev.misei.domain;

public abstract class DomainException extends RuntimeException {

    protected DomainException() {
    }

    protected DomainException(String message) {
        super(message);
    }
}

