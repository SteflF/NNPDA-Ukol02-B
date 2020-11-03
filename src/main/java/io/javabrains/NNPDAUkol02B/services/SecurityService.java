package io.javabrains.NNPDAUkol02B.services;

import io.javabrains.NNPDAUkol02B.model.RecoverPasswordInputModel;
import io.javabrains.NNPDAUkol02B.model.ResetPasswordInputModel;

public interface SecurityService {
    String recoverPassword(RecoverPasswordInputModel inputModel);

    boolean resetPassword(ResetPasswordInputModel inputModel);

    boolean resetPasswordTokenValidation(String token);
}
