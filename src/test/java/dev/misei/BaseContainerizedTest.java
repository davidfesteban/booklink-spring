package dev.misei;

import dev.misei.repository.AppointmentRepository;
import dev.misei.repository.AuthRepository;
import dev.misei.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
//@SpringBootTest
public class BaseContainerizedTest {

    @Container
    static MongoDBContainer container = new MongoDBContainer(DockerImageName.parse("mongo:5"));
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private AuthRepository authRepository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", container::getReplicaSetUrl);
    }
}