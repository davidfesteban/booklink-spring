package dev.misei.application;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@AllArgsConstructor
public class MailService {

    private RestTemplate restTemplate;

    public void sendConfirmationMessage(String to, String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer $SENDGRID_API_KEY");

        var builder = UriComponentsBuilder.fromHttpUrl("https://api.sendgrid.com/v3/mail/send");
        // Create the JSON payload for the email
        var payload = "{"
                + "\"personalizations\": [{\"to\": [{\"email\": \"" + to + "\"}]}],"
                + "\"from\": {\"email\": \"test@example.com\"},"
                + "\"subject\": \"Sending with SendGrid is Fun\","
                + "\"content\": [{\"type\": \"text/plain\", \"value\": \"" + text + "\"}]"
                + "}";

        HttpEntity<?> entity = new HttpEntity<>(payload, headers);

        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                entity,
                String.class);
    }
}
