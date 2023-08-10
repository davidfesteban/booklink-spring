package dev.misei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableMongoRepositories
public class BooklinkApplication {
    //TODO: Keep a reference to each appointment in memory and delete each day.
    public static void main(String[] args) {
        SpringApplication.run(BooklinkApplication.class, args);
    }

}