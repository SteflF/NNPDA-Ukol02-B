package io.javabrains.nnpda.services;

import io.javabrains.nnpda.model.ChangePasswordInputModel;
import io.javabrains.nnpda.model.User;
import io.javabrains.nnpda.model.UserInputModel;

public interface UserService {
    User findOne(String username);

    User save(UserInputModel user);

    Boolean changePassword(ChangePasswordInputModel inputModel);
}
