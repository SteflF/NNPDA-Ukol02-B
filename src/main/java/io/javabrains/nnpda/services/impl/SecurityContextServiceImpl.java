package io.javabrains.nnpda.services.impl;

import io.javabrains.nnpda.model.db.User;
import io.javabrains.nnpda.services.SecurityContextService;
import io.javabrains.nnpda.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service("securityContextService")
public class SecurityContextServiceImpl implements SecurityContextService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    @Autowired
    public SecurityContextServiceImpl(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Override
    public User GetAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principals = auth.getPrincipal();

        if (principals instanceof UserDetails) {
            return userService.findOne(((UserDetails) principals).getUsername());
        }

        return null;
    }

    @Override
    public User AuthenticateUser(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return userService.findOne(username);
        } catch (Exception ex) {
            log.warn("Invalid Credentials: \n  username: " + username + "\n  password: " + password);
        }
        return null;
    }
}
