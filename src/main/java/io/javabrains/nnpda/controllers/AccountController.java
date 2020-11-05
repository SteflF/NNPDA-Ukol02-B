package io.javabrains.nnpda.controllers;

import io.javabrains.nnpda.model.ApiResponse;
import io.javabrains.nnpda.config.JwtUtil;
import io.javabrains.nnpda.model.*;
import io.javabrains.nnpda.services.SecurityService;
import io.javabrains.nnpda.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping( value = "/security")
public class AccountController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final SecurityService securityService;

    private final JwtUtil jwtUtil;

    public AccountController(AuthenticationManager authenticationManager, UserService userService, SecurityService securityService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.securityService = securityService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    @RequestMapping(value = "/authenticate")
    public ApiResponse<AuthenticationResponse> Authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {
        User user = doAuthenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        if (user != null) {
            final String jwtToken = jwtUtil.generateToken(user);
            AuthenticationResponse response = new AuthenticationResponse(user.getUsername(), user.getRole().getName(), jwtToken);

            return new ApiResponse<>(200, "SUCCESS", response);
        }

        return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "INVALID-CREDENTIALS", null);
    }

    private User doAuthenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return userService.findOne(username);
        } catch (Exception ex) {
            log.warn("Invalid Credentials: \n  username: " + username + "\n  password: " + password);
        }
        return null;
    }

    @PostMapping
    @RequestMapping(value = "/registerUser")
    public ResponseEntity<?> RegisterUser(@RequestBody UserInputModel user) {
        var newUser = userService.save(user);

        if (newUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body(newUser);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    // Poslani reset tokenu na zadanou email adresu
    @PostMapping
    @RequestMapping(value = "/recoverPassword")
    public ResponseEntity<?> RecoverPassword(@RequestBody RecoverPasswordInputModel inputModel) {
        String token = securityService.recoverPassword(inputModel);

        if (!token.isBlank()) {
            return ResponseEntity.status(HttpStatus.OK).body("Token: " + token);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Chyba");
    }

    // Nastaveni noveho hesla
    @PostMapping
    @RequestMapping(value = "/resetPassword")
    public ResponseEntity<?> ResetPassword(@RequestBody ResetPasswordInputModel inputModel) {
        Boolean result = securityService.resetPassword(inputModel);

        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body("Reset hesla proběhl úspěšně.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Chyba.");
    }

    // Overeni platnosti tokenu pro vytvoreni noveho hesla
    @PostMapping
    @RequestMapping(value = "/validateResetPasswordToken/{token}")
    public ResponseEntity<?> TokenValidation(@PathVariable String token) {
        Boolean isValid = securityService.resetPasswordTokenValidation(token);

        if (isValid) {
            return ResponseEntity.status(HttpStatus.OK).body("Token je validni: " + token);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token je nevalidni.");
    }
}
