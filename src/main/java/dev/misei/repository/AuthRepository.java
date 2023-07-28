package dev.misei.repository;

import dev.misei.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends MongoRepository<User, String> {
    Optional<User> findByAppointments_Id(String id);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean deleteUserByEmail(String email);
}
