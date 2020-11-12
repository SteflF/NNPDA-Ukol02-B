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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bcryptEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final RecoverTokenRepository recoverTokenRepository;

    @Autowired
    public SecurityServiceImpl(AuthenticationManager authenticationManager, BCryptPasswordEncoder bcryptEncoder, UserRepository userRepository, EmailService emailService, RecoverTokenRepository recoverTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.bcryptEncoder = bcryptEncoder;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.recoverTokenRepository = recoverTokenRepository;
    }

    @Override
    public User GetAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principals = auth.getPrincipal();

        if (principals instanceof UserDetails) {
            User user = userRepository.findByUsername(((UserDetails) principals).getUsername());
            return user != null ? user : null;
            //return userService.findOne(((UserDetails) principals).getUsername());
        }

        return null;
    }

    @Override
    public User AuthenticateUser(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return userRepository.findByUsername(username);
        } catch (Exception ex) {
            log.warn("Invalid Credentials: \n  username: " + username + "\n  password: " + password);
        }
        return null;
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

            user.setRecoverToken(recoverToken);
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
            user.setPassword(bcryptEncoder.encode(inputModel.getPassword()));
            user.setRecoverToken(null);
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

        return recoverToken != null ? true : false;
    }

    private String generateUUID(){
        UUID uuid = Generators.timeBasedGenerator().generate();

        return uuid.toString();
    }
}
