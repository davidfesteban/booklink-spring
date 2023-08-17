package dev.misei.domain.business;

import java.time.LocalDate;

public record DeviatingHoursByDay(LocalDate localDate, OpeningHoursByDay openingHoursByDay) {
}
