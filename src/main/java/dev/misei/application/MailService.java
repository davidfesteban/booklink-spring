package dev.misei.application;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MailService {

    private JavaMailSender emailSender;

    public void sendConfirmationMessage(String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        message.setTo(to);
        message.setSubject("Your login confirmation");
        message.setText(text);
        emailSender.send(message);
    }
}
