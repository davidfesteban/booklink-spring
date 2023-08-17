package dev.misei.domain.business;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public record DeviatingHours(List<DeviatingHoursByDay> deviatingHoursByDays) {
    public Optional<DeviatingHoursByDay> getOpeningHours(LocalDate date) {
        return deviatingHoursByDays.stream().filter(it -> it.localDate().equals(date)).findFirst();
    }
}
