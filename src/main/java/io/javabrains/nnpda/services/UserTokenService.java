package io.javabrains.nnpda.services;

import io.javabrains.nnpda.model.UserToken;

public interface UserTokenService {
    UserToken findById(int id);

    UserToken findByName(String token);
}
