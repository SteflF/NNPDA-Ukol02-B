package io.javabrains.nnpda.services.impl;

import com.fasterxml.uuid.Generators;
import io.javabrains.nnpda.model.db.RecoverToken;
import io.javabrains.nnpda.model.dto.RecoverPasswordInputModel;
import io.javabrains.nnpda.model.dto.ResetPasswordInputModel;
import io.javabrains.nnpda.model.db.User;
import io.javabrains.nnpda.repository.RecoverTokenRepository;
import io.javabrains.nnpda.repository.UserRepository;
import io.javabrains.nnpda.services.EmailService;
import io.javabrains.nnpda.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    private final EmailService emailService;

    private final RecoverTokenRepository recoverTokenRepository;

    @Autowired
    public SecurityServiceImpl(UserRepository userRepository, EmailService emailService, RecoverTokenRepository recoverTokenRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.recoverTokenRepository = recoverTokenRepository;
    }

    @Override
    public String recoverPassword(RecoverPasswordInputModel inputModel) {
        String userName = inputModel.getUsername();
        User user = userRepository.findByUsername(userName);

        if (user != null) {
            String token = generateUUID();

            RecoverToken recoverToken = new RecoverToken();
            recoverToken.setToken(token);
            recoverTokenRepository.save(recoverToken);

            //user.setRecoverToken(recoverToken);
            userRepository.save(user);

            emailService.sendPasswordRecoveryEmail(inputModel.getEmail(), token);

            return token;
        }

        return "";
    }

    @Override
    public boolean resetPassword(ResetPasswordInputModel inputModel) {
        User user = userRepository.findByRecoverToken_Token(inputModel.getToken());

        if (user != null){
            user.setPassword(inputModel.getPassword());
            //user.setRecoverToken(null);
            userRepository.save(user);

            RecoverToken recoverToken = recoverTokenRepository.findByToken(inputModel.getToken());
            recoverTokenRepository.delete(recoverToken);

            return true;
        }

        return false;
    }

    @Override
    public boolean resetPasswordTokenValidation(String token) {
        RecoverToken recoverToken = recoverTokenRepository.findByToken(token);

        if (recoverToken != null) {
            return true;
        }

        return false;
    }

    private String generateUUID(){
        UUID uuid = Generators.timeBasedGenerator().generate();

        return uuid.toString();
    }
}
