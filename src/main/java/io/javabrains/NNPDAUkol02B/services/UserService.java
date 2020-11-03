package io.javabrains.NNPDAUkol02B.services;

import io.javabrains.NNPDAUkol02B.model.ChangePasswordInputModel;
import io.javabrains.NNPDAUkol02B.model.User;
import io.javabrains.NNPDAUkol02B.model.UserInputModel;

public interface UserService {
    User findOne(String userName);

    User save(UserInputModel user);

    Boolean changePassword(ChangePasswordInputModel inputModel);
}
