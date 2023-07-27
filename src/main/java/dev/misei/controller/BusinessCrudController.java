package dev.misei.controller;

import dev.misei.application.BusinessProcessor;
import dev.misei.config.jwt.JwtTokenProvider;
import dev.misei.domain.payload.BusinessPayload;
import dev.misei.repository.AuthRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/private/business")
public class BusinessCrudController extends BaseCrudController {

    private final BusinessProcessor businessProcessor;

    public BusinessCrudController(JwtTokenProvider jwtTokenProvider, AuthRepository authRepository, BusinessProcessor businessProcessor) {
        super(jwtTokenProvider, authRepository);
        this.businessProcessor = businessProcessor;
    }

    @PostMapping("/create")
    public ResponseEntity<BusinessPayload> createBusiness(@RequestBody BusinessPayload businessPayload, @RequestHeader("Authorization") String tokenRequest) {
        return perform(user -> businessProcessor.createBusiness(businessPayload, user), tokenRequest);
    }

    @GetMapping("/details")
    public ResponseEntity<BusinessPayload> findBusinessDetails(@RequestHeader("Host") String domain) {
        try {
            return ResponseEntity.ok(businessProcessor.findBusiness(processDomain(domain)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).header("Message", e.getMessage()).build();
        }
    }

    @PostMapping("/modify")
    public ResponseEntity<BusinessPayload> modifyBusinessDetails(@RequestBody BusinessPayload businessPayload, @RequestHeader("Authorization") String tokenRequest) {
        return perform(userEmail -> businessProcessor.modifyBusinessDetails(businessPayload, userEmail), tokenRequest);
    }
}
