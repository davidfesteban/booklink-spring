package dev.misei.domain.business;


import dev.misei.domain.core.TimeInterval;

import java.util.List;

public record OpeningHoursByDay(List<TimeInterval> intervals) {
    public boolean isAvailableForInterval(TimeInterval timeInterval) {
        return intervals.stream().filter(it -> timeInterval.fitsInside(it)).findFirst().isPresent();
    }
}
