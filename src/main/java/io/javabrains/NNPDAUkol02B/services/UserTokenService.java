package io.javabrains.NNPDAUkol02B.services;

import io.javabrains.NNPDAUkol02B.model.UserToken;

public interface UserTokenService {
    UserToken findById(int id);

    UserToken findByName(String token);
}
