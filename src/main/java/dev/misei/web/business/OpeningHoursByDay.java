package dev.misei.web.business;


import java.util.List;

public record OpeningHoursByDay(List<TimeInterval> intervals) {
}
