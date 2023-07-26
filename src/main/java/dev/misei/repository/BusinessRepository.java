package dev.misei.repository;

import dev.misei.domain.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<Business, String> {
    Optional<Business> findBySubdomainIgnoreCase(String subdomain);

    Boolean existsBySubdomainIgnoreCase(String subdomain);

    void deleteBySubdomain(String subdomain);
}
