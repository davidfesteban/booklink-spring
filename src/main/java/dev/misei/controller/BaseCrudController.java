package dev.misei.controller;


import dev.misei.config.jwt.JwtTokenProvider;
import dev.misei.domain.ActionResult;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public abstract class BaseCrudController {
    private JwtTokenProvider jwtTokenProvider;

    <T> ResponseEntity<T> perform(Function<String, ActionResult<T>> action, String tokenRequest) {
        var userEmail = processEmail(tokenRequest);

        if (userEmail != null && !userEmail.isEmpty()) {
            var result = action.apply(userEmail);
            if (result.isResult() && result.getReturnable() != null) {
                return ResponseEntity.ok(result.getReturnable());
            } else if (result.isResult()) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).header("Message", result.getMessage()).build();
            } else {
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).header("Message", result.getMessage()).build();
            }
        }

        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).header("Message", "Bad Token").build();
    }


    String processEmail(String tokenRequest) {
        return jwtTokenProvider.getUserEmailFromToken(tokenRequest);
    }

    public String processDomain(String domain) {
        String patternString = "^(?:https?://)?(?:www\\.)?(.*?)\\.(booklink\\.dev)$";
        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(domain);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }
}
