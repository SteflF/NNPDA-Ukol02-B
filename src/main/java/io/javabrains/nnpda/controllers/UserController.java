package io.javabrains.nnpda.controllers;

import io.javabrains.nnpda.model.ChangePasswordInputModel;
import io.javabrains.nnpda.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping
    @ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken")})
    @RequestMapping(value = "/changePassword")
    public ResponseEntity<?> ChangePassword(@RequestBody ChangePasswordInputModel inputModel) {
        if (userService.changePassword(inputModel)) {
            return ResponseEntity.status(HttpStatus.OK).body("Heslo uspesne zmeneno");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Neco se nepovedlo");
    }
}
