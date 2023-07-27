package dev.misei.repository;

import dev.misei.domain.entity.Business;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessRepository extends MongoRepository<Business, String> {
    Optional<Business> findBySubdomainIgnoreCase(String subdomain);

    Boolean existsBySubdomainIgnoreCase(String subdomain);

    void deleteBySubdomain(String subdomain);
}
