package io.javabrains.nnpda.services;

import io.javabrains.nnpda.model.RecoverPasswordInputModel;
import io.javabrains.nnpda.model.ResetPasswordInputModel;

public interface SecurityService {
    String recoverPassword(RecoverPasswordInputModel inputModel);

    boolean resetPassword(ResetPasswordInputModel inputModel);

    boolean resetPasswordTokenValidation(String token);
}
