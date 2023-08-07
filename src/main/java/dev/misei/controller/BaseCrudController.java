package dev.misei.controller;


import dev.misei.domain.BooklinkException;
import dev.misei.config.jwt.JwtTokenProvider;
import dev.misei.domain.entity.User;
import dev.misei.repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public abstract class BaseCrudController {

    private JwtTokenProvider jwtTokenProvider;
    private AuthRepository authRepository;

    <T> ResponseEntity<T> perform(Function<User, T> action, String tokenRequest) {
        var userEmail = processEmail(tokenRequest);

        try {
            var user = authRepository.findByEmail(userEmail).orElseThrow(BooklinkException.Type.USER_NOT_FOUND::boom);
            return ResponseEntity.ok(action.apply(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).header("Message", e.getMessage()).build();
        }
    }


    String processEmail(String tokenRequest) {
        return jwtTokenProvider.getUserEmailFromToken(tokenRequest);
    }

    public String processDomain(String domain) {
        String patternString = "^(?:https?://)?(?:www\\.)?(.*?)\\.(booklink\\.app)$";
        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(domain);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }
}
