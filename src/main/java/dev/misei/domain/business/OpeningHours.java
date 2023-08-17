package dev.misei.domain.business;

import java.util.List;

public record OpeningHours(OpeningHoursByDay monday, OpeningHoursByDay tuesday, OpeningHoursByDay wednesday,
                           OpeningHoursByDay thursday, OpeningHoursByDay friday, OpeningHoursByDay saturday,
                           OpeningHoursByDay sunday

) {
    public List<OpeningHoursByDay> getAll() {
        return List.of(monday, tuesday, wednesday, thursday, friday, saturday, sunday);
    }
}
