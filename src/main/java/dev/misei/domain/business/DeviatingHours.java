package dev.misei.domain.business;

import java.util.List;

public record DeviatingHours(List<DeviatingHoursByDay> deviatingHoursByDays) {
}
