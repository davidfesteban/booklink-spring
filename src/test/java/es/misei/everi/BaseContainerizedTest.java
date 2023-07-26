package es.misei.everi;

import es.misei.everi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest
public class BaseContainerizedTest {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    AuthRepository authRepository;

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.32"));

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        //registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("spring.datasource.url", () -> mySQLContainer.getJdbcUrl());
        registry.add("spring.datasource.driverClassName", () -> mySQLContainer.getDriverClassName());
        registry.add("spring.datasource.username", () -> mySQLContainer.getUsername());
        registry.add("spring.datasource.password", () -> mySQLContainer.getPassword());
        //registry.add("spring.flyway.enabled", () -> "true");
    }
}