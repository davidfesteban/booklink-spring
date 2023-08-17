package dev.misei.web.business;

import java.time.LocalDate;

public record DeviatingHoursByDay(LocalDate localDate, OpeningHoursByDay openingHoursByDay) {
}
