package io.javabrains.nnpda.services.impl;

import io.javabrains.nnpda.services.EmailSenderService;
import io.javabrains.nnpda.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

    private final EmailSenderService emailSenderService;

    @Autowired
    public EmailServiceImpl(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @Override
    public void sendPasswordRecoveryEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();

        StringBuilder text = new StringBuilder();
        text.append("Dobrý den,\n");
        text.append("zasíláme Vám odkaz pro resetování hesla:\n");
        text.append("http://localhost:8080/authenticate/validateResetPasswordToken/"+token);

        message.setFrom("noreply@baeldung.com");
        message.setTo(to);
        message.setSubject("Password recovery");
        message.setText(text.toString());

        emailSenderService.sendEmail(message);
    }
}
