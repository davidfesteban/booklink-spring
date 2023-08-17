package dev.misei.web.business;

public record OpeningHours(OpeningHoursByDay monday, OpeningHoursByDay tuesday, OpeningHoursByDay wednesday,
                           OpeningHoursByDay thursday, OpeningHoursByDay friday, OpeningHoursByDay saturday,
                           OpeningHoursByDay sunday

) {
}
