package io.javabrains.nnpda.services;

import io.javabrains.nnpda.model.dto.ChangePasswordInputModel;
import io.javabrains.nnpda.model.db.User;
import io.javabrains.nnpda.model.dto.UserInputModel;

public interface UserService {
    User findOne(String username);

    User save(UserInputModel user);

    Boolean changePassword(ChangePasswordInputModel inputModel);

    Boolean alreadyRegistered(String username);
}
