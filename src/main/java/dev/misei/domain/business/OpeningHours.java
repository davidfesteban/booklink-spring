package dev.misei.domain.business;

public record OpeningHours(OpeningHoursByDay monday, OpeningHoursByDay tuesday, OpeningHoursByDay wednesday,
                           OpeningHoursByDay thursday, OpeningHoursByDay friday, OpeningHoursByDay saturday,
                           OpeningHoursByDay sunday

) {
}
