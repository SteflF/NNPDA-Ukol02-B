package io.javabrains.nnpda.controllers;

import io.javabrains.nnpda.model.ApiResponse;
import io.javabrains.nnpda.model.dto.*;
import io.javabrains.nnpda.config.JwtUtil;
import io.javabrains.nnpda.model.db.User;
import io.javabrains.nnpda.services.SecurityService;
import io.javabrains.nnpda.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping( value = "/api/security")
public class AccountController {

    private final UserService userService;
    private final SecurityService securityService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AccountController(UserService userService, SecurityService securityService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.securityService = securityService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/authenticate")
    public ApiResponse<AuthenticationResponse> Authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {
        //User user = doAuthenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        //User user = securityContextService.AuthenticateUser(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        User user = securityService.AuthenticateUser(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        if (user != null) {
            final String jwtToken = jwtUtil.generateToken(user);
            AuthenticationResponse response = new AuthenticationResponse(user.getUsername(), user.getRole().getName(), jwtToken);

            return new ApiResponse<>(200, "SUCCESS", response);
        }

        return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "INVALID-CREDENTIALS", null);
    }
/*
    private User doAuthenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return userService.findOne(username);
        } catch (Exception ex) {
            log.warn("Invalid Credentials: \n  username: " + username + "\n  password: " + password);
        }
        return null;
    }
*/
    @PostMapping("/registerUser")
    public ApiResponse<User> RegisterUser(@RequestBody UserInputModel user) {
        if (userService.alreadyRegistered(user.getUsername())) {
            return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "ALREADY-EXISTS", null);
        }

        User newUser = userService.save(user);

        if (newUser != null) {
            return new ApiResponse<>(200, "SUCCESS", null);
        }

        return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "ERROR", null);
    }

    @PostMapping("/recoverPassword")
    public ApiResponse<String> RecoverPassword(@RequestBody RecoverPasswordInputModel inputModel) {
        String token = securityService.recoverPassword(inputModel);

        if (!token.isBlank()) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }

        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", null);
    }

    @PostMapping("/resetPassword")
    public ApiResponse<Void> ResetPassword(@RequestBody ResetPasswordInputModel inputModel) {
        Boolean result = securityService.resetPassword(inputModel);

        if (result) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }

        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", null);
    }

    @PostMapping("/validateResetPasswordToken/{token}")
    public ApiResponse<Void> TokenValidation(@PathVariable String token) {
        Boolean isValid = securityService.resetPasswordTokenValidation(token);

        if (isValid) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }

        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", null);
    }

    @PutMapping("/changePassword")
    @ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken")})
    public ApiResponse<Void> ChangePassword(@RequestBody ChangePasswordInputModel inputModel) {
        if (userService.changePassword(inputModel)) {
            return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        }

        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "BAD_REQUEST", null);
    }
}
