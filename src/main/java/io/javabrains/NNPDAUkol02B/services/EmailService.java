package io.javabrains.NNPDAUkol02B.services;

public interface EmailService {

    void sendPasswordRecoveryEmail(String to, String token);
}
