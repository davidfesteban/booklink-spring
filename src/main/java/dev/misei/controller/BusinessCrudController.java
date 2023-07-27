package dev.misei.controller;

import dev.misei.application.BusinessProcessor;
import dev.misei.config.jwt.JwtTokenProvider;
import dev.misei.domain.payload.business.BusinessPayload;
import dev.misei.domain.payload.business.SimpleBusinessPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/private/business")
public class BusinessCrudController extends BaseCrudController {

    private final BusinessProcessor businessProcessor;

    public BusinessCrudController(JwtTokenProvider jwtTokenProvider, BusinessProcessor businessProcessor) {
        super(jwtTokenProvider);
        this.businessProcessor = businessProcessor;
    }

    @PostMapping("/create")
    public ResponseEntity<SimpleBusinessPayload> createBusiness(@RequestBody SimpleBusinessPayload organizationPayload, @RequestHeader("Authorization") String tokenRequest) {
        return perform(userEmail -> businessProcessor.createBusiness(organizationPayload, userEmail), tokenRequest);
    }

    @GetMapping("/details")
    public ResponseEntity<BusinessPayload> findBusinessDetails(@RequestHeader("Host") String domain) {
        var result = businessProcessor.findBusiness(processDomain(domain));

        return result.isResult() ? ResponseEntity.ok(result.getReturnable()) : ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).header("Message", "Business not found").build();
    }

    @PostMapping("/modify")
    public ResponseEntity<BusinessPayload> modifyBusinessDetails(@RequestBody SimpleBusinessPayload organizationPayload, @RequestHeader("Authorization") String tokenRequest) {
        return perform(userEmail -> businessProcessor.modifyBusiness(organizationPayload, userEmail), tokenRequest);
    }
}
