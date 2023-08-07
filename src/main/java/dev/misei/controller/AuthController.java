package dev.misei.controller;

import dev.misei.application.MailService;
import dev.misei.config.jwt.JwtTokenProvider;
import dev.misei.domain.mapper.JoinToUserMapper;
import dev.misei.domain.payload.UserPayload;
import dev.misei.repository.AuthRepository;
import dev.misei.repository.TokenRepository;
import dev.misei.repository.UserInMemoryWaiting;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/public/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private TokenRepository tokenRepository;
    private UserInMemoryWaiting userInMemoryWaiting;
    private AuthRepository authRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;
    private MailService mailService;

    //TODO: Re-route message errors through header
    @GetMapping("/login")
    public ResponseEntity<String> authenticateUser(String email, String password) {
        try {
            //TODO: Send password as encrypted from frontend
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    email, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            var savedToken = tokenRepository.retrieveGeneratedToken(email);

            if (savedToken.isEmpty() || !tokenProvider.validateToken(savedToken.get())) {
                String token = tokenProvider.generateToken(email, password);
                tokenRepository.saveOverrideGeneratedToken(email, token);
                return ResponseEntity.ok(token);
            }
            return ResponseEntity.ok(savedToken.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).header("Message", e.getMessage()).build();
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmUser(String uuid) {
        try {
            var user = userInMemoryWaiting.confirm(UUID.fromString(uuid));
            authRepository.save(new JoinToUserMapper(passwordEncoder).apply(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).header("Message", e.getMessage()).build();
        }

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/join")
    public ResponseEntity<?> registerUser(@RequestBody UserPayload userPayload) {
        //TODO: Add antispam, maybe as a filter
        if (authRepository.existsByEmail(userPayload.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        try {
            var confirmationToken = userInMemoryWaiting.sendConfirmation(userPayload);
            mailService.sendConfirmationMessage(userPayload.getEmail(),
                    "http://localhost:8080/api/public/auth/confirm?uuid=" + confirmationToken.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).header("Message", e.getMessage()).build();
        }

        return ResponseEntity.ok("Check your email");
    }
}