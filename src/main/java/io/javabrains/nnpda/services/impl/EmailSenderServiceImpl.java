package io.javabrains.nnpda.services.impl;

import io.javabrains.nnpda.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("emailSenderService")
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailSenderServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(SimpleMailMessage message) {
        mailSender.send(message);
    }
}
