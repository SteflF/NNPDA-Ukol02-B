package io.javabrains.NNPDAUkol02B.services.impl;

import io.javabrains.NNPDAUkol02B.services.EmailSenderService;
import io.javabrains.NNPDAUkol02B.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailSenderService emailSenderService;

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
