package dev.misei.domain.business;


import java.util.List;

public record OpeningHoursByDay(List<TimeInterval> intervals) {
}
