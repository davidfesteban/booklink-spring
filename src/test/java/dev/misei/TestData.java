package dev.misei;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;

@TestComponent
public class TestData {
    @Value("classpath:joinDavid.json")
    Resource joinDavid;
    @Value("classpath:joinWho.json")
    Resource joinWho;
    @Value("classpath:createCompany.json")
    Resource createCompany;
    @Value("classpath:createCompany2.json")
    Resource createCompany2;
    @Value("classpath:appointmentCreate.json")
    Resource appointmentCreate;
    @Value("classpath:appointmentCreate2.json")
    Resource appointmentCreate2;
    @Value("classpath:appointmentCreateFailNonWorkingHours.json")
    Resource appointmentCreateFailNonWorkingHours;
    @Value("classpath:appointmentCreateFailSpecialWorkingHours.json")
    Resource appointmentCreateFailSpecialWorkingHours;
    @Value("classpath:modifyCompany.json")
    Resource modifyCompany;
}
