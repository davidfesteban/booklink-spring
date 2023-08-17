package dev.misei.domain.customer;

import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends Repository<Customer, UUID> {
    Optional<Customer> getByAuthId(String authId);
}
