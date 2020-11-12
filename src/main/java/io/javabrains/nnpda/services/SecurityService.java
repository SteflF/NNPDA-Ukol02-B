package io.javabrains.nnpda.services;

import io.javabrains.nnpda.model.db.User;
import io.javabrains.nnpda.model.dto.RecoverPasswordInputModel;
import io.javabrains.nnpda.model.dto.ResetPasswordInputModel;

public interface SecurityService {
    User GetAuthenticatedUser();

    User AuthenticateUser(String username, String password);

    String recoverPassword(RecoverPasswordInputModel inputModel);

    boolean resetPassword(ResetPasswordInputModel inputModel);

    boolean resetPasswordTokenValidation(String token);
}
