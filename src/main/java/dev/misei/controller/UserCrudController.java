package dev.misei.controller;

import dev.misei.application.UserProcessor;
import dev.misei.config.jwt.JwtTokenProvider;
import dev.misei.domain.payload.user.UserPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/user")
public class UserCrudController extends BaseCrudController {

    private final UserProcessor userProcessor;

    public UserCrudController(JwtTokenProvider jwtTokenProvider, UserProcessor userProcessor) {
        super(jwtTokenProvider);
        this.userProcessor = userProcessor;
    }

    @GetMapping("/findUserByEmail")
    public ResponseEntity<UserPayload> findUserByEmail(@RequestHeader("Authorization") String tokenRequest) {
        return ResponseEntity.ok(userProcessor.findByEmail(processEmail(tokenRequest)));
    }

    /*@GetMapping("/addUser")
    public ResponseEntity<String> addUser(String organizationCode, String userToAddEmail, boolean asAdmin, @RequestHeader("Authorization") String tokenRequest) {
        return perform(userEmail -> userProcessor.addUser(organizationCode, userEmail, userToAddEmail, asAdmin), tokenRequest);
    }

    @GetMapping("/removeUser")
    public ResponseEntity<String> removeUser(String organizationCode, @RequestHeader("Authorization") String tokenRequest) {
        return perform(userEmail -> userProcessor.removeUser(organizationCode, userEmail), tokenRequest);
    }

    @GetMapping("/removeUserAsAdmin")
    public ResponseEntity<String> removeUserAsAdmin(String organizationCode, String userToRemoveEmail, @RequestHeader("Authorization") String tokenRequest) {
        return perform(userEmail -> userProcessor.removeUserAsAdmin(organizationCode, userEmail, userToRemoveEmail), tokenRequest);
    }

    @GetMapping("/addUserToWaitlist")
    public ResponseEntity<String> addUserToWaitlist(String organizationCode, @RequestHeader("Authorization") String tokenRequest) {
        return perform(userEmail -> userProcessor.addUserToWaitlist(organizationCode, userEmail), tokenRequest);
    }*/
}
