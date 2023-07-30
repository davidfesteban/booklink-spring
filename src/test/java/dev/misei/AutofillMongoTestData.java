package dev.misei;

import dev.misei.domain.WorkingHours;
import dev.misei.domain.entity.Appointment;
import dev.misei.domain.entity.Business;
import dev.misei.domain.entity.User;
import dev.misei.repository.AuthRepository;
import dev.misei.repository.BusinessRepository;
import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Set;

/**
 * It is easier for me to do it here than in the mongo-init.js but writing directly on repositories are not a good idea
 * It is better to use the already implemented flow
 */

@SpringBootTest
@TestOnly
public class AutofillMongoTestData {

    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private BusinessRepository businessRepository;

    @Test
    void addUsers() {

        var workingHours = new WorkingHours(LocalTime.of(8, 0), LocalTime.of(16, 0), LocalTime.of(12, 0), LocalTime.of(13, 0));

        var business = new Business();
        business.setName("peluqueria");
        business.setPhone("123456789");
        business.setAddress("Here and there");
        business.setSubdomain("peluqueria");
        business.setEmail("peluqueria@booklink.app");
        business.setSlotOwners(Set.of("JUAN", "CARLA"));
        business.setWorkingHoursByDay(Map.of(
                DayOfWeek.MONDAY, workingHours,
                DayOfWeek.TUESDAY, workingHours,
                DayOfWeek.WEDNESDAY, workingHours,
                DayOfWeek.THURSDAY, workingHours,
                DayOfWeek.FRIDAY, workingHours,
                DayOfWeek.SATURDAY, workingHours,
                DayOfWeek.SUNDAY, workingHours));

        business.setSpecialWorkingDays(Map.of(LocalDate.now(), workingHours));
        business.setSlotDurationByServices(Map.of("CORTELARGO", 60L, "MASAJE", 90L));


        var user = new User();
        user.setName("Carlos");
        user.setEmail("carlos@booklink.app");
        user.setPhone("123456789");
        user.setPassword("carlos");
        user.setBusiness(business);


        businessRepository.save(business);
        authRepository.save(user);

    }
}
