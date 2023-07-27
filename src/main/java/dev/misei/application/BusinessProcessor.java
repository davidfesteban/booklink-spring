package dev.misei.application;

import dev.misei.domain.ActionResult;
import dev.misei.domain.mapper.business.BusinessMapper;
import dev.misei.domain.mapper.business.SimpleBusinessMapper;
import dev.misei.domain.payload.business.BusinessPayload;
import dev.misei.domain.payload.business.SimpleBusinessPayload;
import dev.misei.repository.AuthRepository;
import dev.misei.repository.BusinessRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BusinessProcessor {

    private final SimpleBusinessMapper simpleBusinessMapper;
    private final BusinessMapper businessMapper;
    @PersistenceContext
    private EntityManager entityManager;
    private BusinessRepository businessRepository;
    private AuthRepository authRepository;

    @Transactional
    public ActionResult<SimpleBusinessPayload> createBusiness(SimpleBusinessPayload businessPayload, String userEmail) {
        var admin = authRepository.findByEmail(userEmail);

        if (admin.isEmpty()) {
            return ActionResult.FALSE("User not found when creating a business");
        }

        if (admin.get().getBusiness() != null) {
            return ActionResult.FALSE("At the moment is not possible to have more than one business");
        }

        if (businessRepository.existsBySubdomainIgnoreCase(businessPayload.getSubdomain())) {
            return ActionResult.FALSE("Already a business with same subdomain");
        }

        var business = simpleBusinessMapper.toEntity(businessPayload);
        business.addAdmin(admin.get());

        return ActionResult.TRUE(simpleBusinessMapper.toPayload(businessRepository.saveAndFlush(business)));
    }

    @Transactional
    public ActionResult<BusinessPayload> findBusiness(String domain) {

        if (businessRepository.existsBySubdomainIgnoreCase(domain)) {
            return ActionResult.TRUE(businessMapper.toPayloadNoSensitive(businessRepository.findBySubdomainIgnoreCase(domain).get()));
        }

        return ActionResult.FALSE("Not found");
    }

    @Transactional
    public ActionResult<BusinessPayload> modifyBusiness(SimpleBusinessPayload businessPayload, String userEmail) {
        var admin = authRepository.findByEmail(userEmail);

        if (admin.isEmpty()) {
            return ActionResult.FALSE("User not found when modifying a business");
        }

        if (!businessRepository.existsBySubdomainIgnoreCase(businessPayload.getSubdomain())) {
            return ActionResult.FALSE("The subdomain doesn´t exist");
        }

        if (admin.get().getBusiness().getSubdomain().equalsIgnoreCase(businessPayload.getSubdomain())) {
            return ActionResult.FALSE("The current user don´t owns the business to modify it");
        }

        var business = simpleBusinessMapper.toEntity(businessPayload);
        business.addAdmin(admin.get());

        // TODO: Improve. This is not ok. We should compare current entity with new one and merge and then, merge the original one with the entity manager
        return ActionResult.TRUE(businessMapper.toPayloadNoSensitive(businessRepository.saveAndFlush(business)));
    }

    //TODO: Remove appointment
    //TODO: Refactor to make it more granular
}
