package dev.misei.controller;

import dev.misei.application.UserProcessor;
import dev.misei.config.jwt.JwtTokenProvider;
import dev.misei.domain.payload.UserPayload;
import dev.misei.repository.AuthRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/user")
public class UserCrudController extends BaseCrudController {

    private final UserProcessor userProcessor;

    public UserCrudController(JwtTokenProvider jwtTokenProvider, AuthRepository authRepository, UserProcessor userProcessor) {
        super(jwtTokenProvider, authRepository);
        this.userProcessor = userProcessor;
    }

    @GetMapping("/findDetails")
    public ResponseEntity<UserPayload> findDetails(@RequestHeader("Authorization") String tokenRequest) {
        return perform(userProcessor::findDetails, tokenRequest);
    }
}
