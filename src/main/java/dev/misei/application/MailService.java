package dev.misei.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class MailService {

    private final RestTemplate restTemplate;

    @Value("${api.sendgrid}")
    private String sendGridKey;

    public void sendConfirmationMessage(String to, String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + sendGridKey);

        var builder = UriComponentsBuilder.fromHttpUrl("https://api.sendgrid.com/v3/mail/send");
        // Create the JSON payload for the email
        var payload = "{"
                + "\"personalizations\": [{\"to\": [{\"email\": \"" + to + "\"}]}],"
                + "\"from\": {\"email\": \"noreply@booklink.app\"},"
                + "\"subject\": \"Sending with SendGrid is Fun\","
                + "\"content\": ["
                + "{\"type\": \"text/html\", \"value\": \"<html>Hi from Booklink!<br>This is your confirmation link: <a href='" + text + "'>" + text + "</a><br><br>Please, do not reply to this email. If this email is not intended for you, remove it.<br>Thanks!</html>\"}"
                + "]"
                + "}";

        HttpEntity<?> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                entity,
                String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed Mail Service");
        }
    }
}
