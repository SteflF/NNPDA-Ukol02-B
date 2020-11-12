package io.javabrains.nnpda.controllers;

import io.javabrains.nnpda.model.ApiResponse;
import io.javabrains.nnpda.model.dto.ChangePasswordInputModel;
import io.javabrains.nnpda.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
