package io.javabrains.nnpda.controllers;

import io.javabrains.nnpda.config.JwtUtil;
import io.javabrains.nnpda.model.*;
import io.javabrains.nnpda.services.SecurityService;
import io.javabrains.nnpda.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping( value = "/security")
public class AccountController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @RequestMapping(value = "/authenticate")
    public ResponseEntity<?> CreateAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            // The Spring Security Authentication Manager calls 'loadUserByUsername' method for getting the user details
            // from the database when authenticating the user details provided by the user.
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword()));
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final User user = userService.findOne(authenticationRequest.getUserName());

        final String jwtToken = jwtUtil.generateToken(user);

        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
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
