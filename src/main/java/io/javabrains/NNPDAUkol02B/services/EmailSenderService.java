package io.javabrains.NNPDAUkol02B.services;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderService {

    void sendEmail(SimpleMailMessage message);
}
