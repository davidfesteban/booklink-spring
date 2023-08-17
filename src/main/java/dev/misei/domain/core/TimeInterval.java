package dev.misei.domain.core;

import java.time.LocalTime;

public record TimeInterval(LocalTime start, LocalTime stop) {
    public boolean fitsInside(TimeInterval other) {
        return start.isBefore(other.start) || start.equals(other.start) && stop.isAfter(other.stop) || stop.equals(other.stop);
    }

    public boolean isConflictingWith(TimeInterval other) {
        return start.isBefore(other.stop) && start.isAfter(other.start);
    }
}
