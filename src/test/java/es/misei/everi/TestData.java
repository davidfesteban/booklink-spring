package es.misei.everi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.core.io.Resource;

@TestComponent
public class TestData {
    @Value("classpath:billingDavid.json")
    Resource billingDavid;
    @Value("classpath:materialComputer.json")
    Resource materialComputer;
    @Value("classpath:organizationMisei.json")
    Resource organizationMisei;
    @Value("classpath:projectIT.json")
    Resource projectIT;
    @Value("classpath:userDavid.json")
    Resource userDavid;
    @Value("classpath:userJan.json")
    Resource userJan;
}
