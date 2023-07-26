package dev.misei.controller;

import dev.misei.config.jwt.JwtTokenProvider;
import dev.misei.domain.mapper.JoinToUserMapper;
import dev.misei.domain.payload.user.UserPayload;
import dev.misei.repository.AuthRepository;
import dev.misei.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/public/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private TokenRepository tokenRepository;
    private AuthRepository authRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody UserPayload payload) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    payload.getEmail(), payload.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            var savedToken = tokenRepository.retrieveGeneratedToken(payload.getEmail());

            if (savedToken.isEmpty() || !tokenProvider.validateToken(savedToken.get())) {
                String token = tokenProvider.generateToken(payload.getEmail(), payload.getPassword());
                tokenRepository.saveOverrideGeneratedToken(payload.getEmail(), token);
                return ResponseEntity.ok(token);
            }
            return ResponseEntity.ok(savedToken.get());
        } catch (Exception e) {
            return new ResponseEntity<>("Bad authentication!. I am a teapot", HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @PostMapping("/join")
    public ResponseEntity<?> registerUser(@RequestBody UserPayload payload) {

        if (authRepository.existsByEmail(payload.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        try {
            authRepository.saveAndFlush(new JoinToUserMapper(passwordEncoder).apply(payload));
        } catch (Exception e) {
            return new ResponseEntity<>("Error on signup!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}