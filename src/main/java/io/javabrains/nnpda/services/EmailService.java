package io.javabrains.nnpda.services;

public interface EmailService {

    void sendPasswordRecoveryEmail(String to, String token);
}
