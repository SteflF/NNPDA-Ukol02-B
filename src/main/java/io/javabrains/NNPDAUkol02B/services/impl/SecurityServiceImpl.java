package io.javabrains.NNPDAUkol02B.services.impl;

import com.fasterxml.uuid.Generators;
import io.javabrains.NNPDAUkol02B.model.RecoverPasswordInputModel;
import io.javabrains.NNPDAUkol02B.model.ResetPasswordInputModel;
import io.javabrains.NNPDAUkol02B.model.User;
import io.javabrains.NNPDAUkol02B.model.UserToken;
import io.javabrains.NNPDAUkol02B.repository.UserRepository;
import io.javabrains.NNPDAUkol02B.repository.UserTokenRepository;
import io.javabrains.NNPDAUkol02B.services.EmailService;
import io.javabrains.NNPDAUkol02B.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    UserTokenRepository userTokenRepository;

    @Override
    public String recoverPassword(RecoverPasswordInputModel inputModel) {
        String userName = inputModel.getUserName();
        User user = userRepository.findByUserName(userName);

        if (user != null) {
            String token = generateUUID();

            UserToken userToken = new UserToken();
            userToken.setName(token);
            userTokenRepository.save(userToken);

            user.setUserToken(userToken);
            userRepository.save(user);

            emailService.sendPasswordRecoveryEmail(inputModel.getEmail(), token);

            return token;
        }

        return "";
    }

    @Override
    public boolean resetPassword(ResetPasswordInputModel inputModel) {
        User user = userRepository.findByUserTokenName(inputModel.getToken());

        if (user != null){
            user.setPassword(inputModel.getPassword());
            user.setUserToken(null);
            userRepository.save(user);

            UserToken userToken = userTokenRepository.findByName(inputModel.getToken());
            userTokenRepository.delete(userToken);

            return true;
        }

        return false;
    }

    @Override
    public boolean resetPasswordTokenValidation(String token) {
        UserToken userToken = userTokenRepository.findByName(token);

        if (userToken != null) {
            return true;
        }

        return false;
    }

    private String generateUUID(){
        UUID uuid = Generators.timeBasedGenerator().generate();

        return uuid.toString();
    }
}
